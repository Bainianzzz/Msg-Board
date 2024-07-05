package com.lab.crud.controller;

import com.lab.crud.pojo.Info;
import com.lab.crud.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Info> login(String username, String password, HttpServletResponse response) {
        Map<String, String> tokens = authService.checkPasswd(username, password);
        if (tokens != null) {
            log.info("{} successfully logged in", username);
            response.addHeader("Authorization", "Bearer " + tokens.get("token"));
            response.addHeader("refresh_token", tokens.get("refresh_token"));
            return ResponseEntity.status(HttpStatus.OK).
                    body(new Info(20001, "登陆成功", tokens));
        } else {
            log.info("{} failed to login", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new Info(40101, "登陆失败", null));
        }
    }
}
