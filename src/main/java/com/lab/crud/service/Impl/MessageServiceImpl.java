package com.lab.crud.service.Impl;

import com.lab.crud.mapper.MessageMapper;
import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getMessagesById(int id, int page) {
        return List.of();
    }

    @Override
    public List<Message> getMessagesByUserId(int id, int page) {
        return List.of();
    }

    @Override
    public void addMessage(Message message) {

    }

    @Override
    public void updateMessage(Message message) {

    }

    @Override
    public void deleteMessage(int id) {

    }
}
