package com.lab.crud.service;

import com.lab.crud.exception.RegisterInfoBlankException;

import javax.security.auth.login.LoginException;
import java.util.Map;

public interface AuthService {
    // 检查用户名和密码是否匹配，若是则返回token和refresh_token
    // token有效期15分钟，refresh_token有效期为2天
    Map<String, String> checkPasswd(String phone, String password) throws LoginException;

    //新增用户
    void createUser(int phone, String password) throws RegisterInfoBlankException;
}
