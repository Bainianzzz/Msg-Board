package com.lab.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lab.crud.pojo.Info;
import com.lab.crud.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    // 用户登录
    // 登录状态可维持两天
    @PostMapping("/login")
    public Info login(String username, String password){
        try {
            String tokens=loginService.checkPasswd(username, password);
            if (tokens!=null){
                return new Info(20000,"登陆成功", tokens);
            }
        }catch (JsonProcessingException e){
            log.error(e.getMessage());
            return new Info(50001,"服务器将tokens序列化为json时出错",null);
        }
        return new Info(40001,"登陆失败",null);
    }

    //刷新token令牌
    //检查refresh_token确保用户处于登陆状态，然后刷新token令牌
    @GetMapping("/refresh_token")
    public Info refreshToken(HttpServletRequest request){
        return null;
    }
}
