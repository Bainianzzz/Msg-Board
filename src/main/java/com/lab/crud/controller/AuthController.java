package com.lab.crud.controller;

import com.lab.crud.exception.RegisterInfoBlankException;
import com.lab.crud.pojo.Info;
import com.lab.crud.pojo.User;
import com.lab.crud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.Map;

@RestController
public class AuthController extends ObjectController {
    @Autowired
    private AuthService authService;

    // 用户登录
    // refresh_token使登录状态可维持两天，但需30分钟刷新一次token令牌
    @PostMapping("/login")
    public ResponseEntity<Info> login(String phone, String password) {
        try {
            Map<String, String> tokens = authService.checkPasswd(phone, password);
            log.info("{} successfully logged in", phone);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new Info(20001, "success", tokens));
        } catch (LoginException e) {
            log.info("{} failed to login", phone);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(new Info(40101, e.getMessage(), null));
        }
    }

    //新增用户
    @PostMapping("/register")
    public ResponseEntity<Info> createUser(@RequestBody User user) {
        try {
            authService.createUser(user.getPhone(), user.getPassword());
            log.info("created user {}", user.getPhone());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Info(20003, "success", null));
        } catch (RegisterInfoBlankException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new Info(40002, e.getMessage(), null));
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new Info(40003, e.getMessage(), null));
        }
    }
}
