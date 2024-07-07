package com.lab.crud.service;

import com.lab.crud.exception.MessageNotFoundException;
import com.lab.crud.pojo.Message;

import java.util.List;

public interface MessageService {
    void getMessagesById(int id,List<Message> messages);
    List<Message> getMessagesByUid(int uid,int page) throws MessageNotFoundException;
    void addMessage(Message message);
    void updateMessage(Message message);
    void deleteMessage(int id);
}
