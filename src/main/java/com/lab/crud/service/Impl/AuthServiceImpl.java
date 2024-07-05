package com.lab.crud.service.Impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.crud.mapper.UserMapper;
import com.lab.crud.service.AuthService;
import com.lab.crud.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    //若用户名和密码成功匹配则生成tokens
    public Map<String, String> checkPasswd(String username, String v_password){
        String password = userMapper.findPasswdByUsername(username);
        if (password != null && password.equals(v_password)) {
            String token = jwtUtils.getJwt(userMapper.findIdByUsername(username), username, 30);
            String refreshToken = jwtUtils.getJwt(userMapper.findIdByUsername(username), username, 60 * 24 * 2);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", token);
            tokens.put("refresh_token", refreshToken);
            return tokens;
        }
        return null;
    }

    @Override
    //校验refresh_token，并签发新的token
    public Map<String, String> refreshTheToken(String refreshToken) throws RuntimeException{
        Claims payLoad = jwtUtils.parseJwt(refreshToken);
        String newToken = jwtUtils.getJwt(payLoad.get("id", Integer.class), payLoad.get("username", String.class), 15);
        return Map.of("token", newToken);
    }
}
