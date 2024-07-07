package com.lab.crud.mapper;

import com.lab.crud.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    Message selectMessageById(int id);
    Message selectMessageByPid(int pid);
    List<Message> selectMessagesByUId(int uid,int begin,int end);
    void insertMessage(Message message);
    void updateMessage(Message message);
    void deleteMessageById(int id);
}
