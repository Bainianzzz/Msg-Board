package com.lab.crud.service.Impl;

import com.lab.crud.exception.UserNotFoundException;
import com.lab.crud.mapper.MessageMapper;
import com.lab.crud.mapper.UserMapper;
import com.lab.crud.pojo.User;
import com.lab.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;

    //查看用户信息
    @Override
    public User getUserById(int id) throws UserNotFoundException {
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    //注销用户，并删除其所有留言
    @Transactional
    @Override
    public void deleteUserById(int id) throws UserNotFoundException {
        User user = userMapper.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        userMapper.deleteUser(id);
        messageMapper.deleteMessageByUId(id);
    }

    //更新用户信息
    @Override
    public void updateUser(User v_user) throws UserNotFoundException {
        User user = userMapper.getUserById(v_user.getId());
        if (user == null) {
            throw new UserNotFoundException();
        }
        userMapper.updateUser(v_user);
    }
}
