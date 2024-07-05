package com.lab.crud.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.crud.mapper.LoginMapper;
import com.lab.crud.service.LoginService;
import com.lab.crud.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String checkPasswd(String username, String v_password) throws JsonProcessingException {
        String password = loginMapper.findPasswdByUsername(username);
        if (password != null && password.equals(v_password)) {
            String token = jwtUtils.getJwt(loginMapper.findIdByUsername(username), username, 15);
            String refreshToken = jwtUtils.getJwt(loginMapper.findIdByUsername(username), username, 60 * 24 * 2);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", token);
            tokens.put("refresh_token", refreshToken);
            return new ObjectMapper().writeValueAsString(tokens);
        }
        return null;
    }
}
