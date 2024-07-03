package com.lab.crud.service.Impl;

import com.lab.crud.mapper.UserMapper;
import com.lab.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
}
