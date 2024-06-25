package com.lab.crud.mapper;

import com.lab.crud.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    void insert(Message message);
    void removeById(int id);
    void updateById(Message message);
    void updateIsDeleteTrue(int id);
    void updateIsDeleteFalse(int id);
    List<Message> selectAll();
    Message selectById(int id);
}
