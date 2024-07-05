package com.lab.crud.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    String findPasswdByUsername(String username);
    int findIdByUsername(String username);
}
