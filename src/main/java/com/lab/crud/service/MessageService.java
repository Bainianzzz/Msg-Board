package com.lab.crud.service;

import com.lab.crud.pojo.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesById(int id,int page);
    List<Message> getMessagesByUserId(int id,int page);
    void addMessage(Message message);
    void updateMessage(Message message);
    void deleteMessage(int id);
}
