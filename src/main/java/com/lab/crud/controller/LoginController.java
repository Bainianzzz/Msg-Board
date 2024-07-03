package com.lab.crud.controller;

import com.lab.crud.pojo.Info;
import com.lab.crud.service.LoginService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Info login(){
        return null;
    }

    @GetMapping("/refresh_token")
    public Info refreshToken(){
        return null;
    }
}
