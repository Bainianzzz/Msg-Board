package com.lab.crud.service.Impl;

import com.lab.crud.exception.RegisterInfoBlankException;
import com.lab.crud.mapper.UserMapper;
import com.lab.crud.pojo.User;
import com.lab.crud.service.AuthService;
import com.lab.crud.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
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
    public Map<String, String> checkPasswd(String phone, String v_password) throws LoginException {
        User user = userMapper.getUserByPhone(phone);
        if (user != null && user.getPassword().equals(v_password)) {
            String token = jwtUtils.getJwt(user.getId(), phone, 15);
            String refreshToken = jwtUtils.getJwt(user.getId(), phone, 60 * 24 * 2);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", "Bearer " + token);
            tokens.put("refresh_token", "Bearer " + refreshToken);
            return tokens;
        } else {
            throw new LoginException("The phone and password entered didn't match");
        }
    }

    //新增用户
    @Override
    public void createUser(int phone, String password) throws RegisterInfoBlankException {
        if (phone == 0 || password == null || password.isEmpty()) {
            throw new RegisterInfoBlankException();
        } else {
            try {
                userMapper.insertUser(phone, password);
            }catch (DuplicateKeyException e){
                throw new DuplicateKeyException("user is already exist");
            }
        }
    }
}
