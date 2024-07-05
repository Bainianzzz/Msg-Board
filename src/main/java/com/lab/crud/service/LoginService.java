package com.lab.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LoginService {
    // 检查用户名和密码是否匹配，若是则返回token和refresh_token
    // token有效期15分钟，refresh_token有效期为2天
    String checkPasswd(String username, String password) throws JsonProcessingException;
}
