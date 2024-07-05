package com.lab.crud.service;

import java.util.Map;

public interface AuthService {
    // 检查用户名和密码是否匹配，若是则返回token和refresh_token
    // token有效期15分钟，refresh_token有效期为2天
    Map<String, String> checkPasswd(String username, String password);
}
