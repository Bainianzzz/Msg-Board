package com.lab.crud.mapper;

import com.lab.crud.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserByPhone(int phone);
    User getUserById(int id);
    void insertUser(int phone, String password);
    void updateUser(User user);
    void deleteUser(int id);
}
