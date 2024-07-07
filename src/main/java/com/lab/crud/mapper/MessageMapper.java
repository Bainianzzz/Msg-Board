package com.lab.crud.mapper;

import com.lab.crud.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> selectMessagesById(int id,int page);
    List<Message> selectMessagesByUserId(int userId,int page);
    void insertMessage(Message message);
    void updateMessage(Message message);
    void deleteMessageById(int id);
}
