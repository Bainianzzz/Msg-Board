package com.lab.crud.service;

import com.lab.crud.exception.RegisterInfoBlankException;
import com.lab.crud.exception.UserNotFoundException;
import com.lab.crud.pojo.User;

public interface UserService {
    //查看用户信息
    User getUserById(int id) throws UserNotFoundException;

    //注销用户
    void deleteUserById(int id) throws UserNotFoundException;

    //更新用户信息
    void updateUser(User v_user) throws UserNotFoundException;
}
