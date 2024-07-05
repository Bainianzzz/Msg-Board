package com.lab.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lab.crud.pojo.Info;
import com.lab.crud.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    // 用户登录
    // refresh_token使登录状态可维持两天，但需30分钟刷新一次token令牌
    @PostMapping("/login")
    public ResponseEntity<Info> login(String username, String password) {
        Map<String, String> tokens = authService.checkPasswd(username, password);
        if (tokens != null) {
            return ResponseEntity.status(HttpStatus.OK).
                    body(new Info(20001, "登陆成功", tokens));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new Info(40101, "登陆失败", null));
        }
    }

    //刷新token令牌
    //检查refresh_token确保用户处于登陆状态，然后刷新token令牌
    @GetMapping("/refresh_token")
    public ResponseEntity<Info> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh_token");
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).
                    body(new Info(42801, "请求头中未包含refresh_token字段，无法刷新token", null));
        } else {
            try {
                Map<String, String> newToken = authService.refreshTheToken(refreshToken);
                return ResponseEntity.status(HttpStatus.OK).
                        body(new Info(20002, "token令牌更新成功", newToken));
            } catch (RuntimeException e) {
                log.error(e.getMessage());
                if (e instanceof MalformedJwtException || e instanceof SignatureException) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                            body(new Info(40102, "refresh_token令牌内容校验失败", null));
                } else if (e instanceof ExpiredJwtException) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                            body(new Info(40103, "refresh_token令牌过期，请重新登录", null));
                }
            }
        }
        return null;
    }
}
