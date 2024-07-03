package com.lab.crud.service.Impl;

import com.lab.crud.mapper.LoginMapper;
import com.lab.crud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
}
