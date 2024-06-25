package com.lab.crud.service.Impl;

import com.lab.crud.exception.EmptyMessageException;
import com.lab.crud.exception.MessageNotFoundException;
import com.lab.crud.mapper.MessageMapper;
import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> findList() {
        return messageMapper.selectAll();
    }

    @Override
    public Message findById(int id) {
        Message message = messageMapper.selectById(id);
        if (message == null) {
            throw new MessageNotFoundException();
        }
        return message;
    }

    @Override
    public void deleteById(int id) {
        if (messageMapper.selectById(id) == null) {
            throw new MessageNotFoundException();
        }
        messageMapper.removeById(id);
    }

    @Override
    public void withdrawById(int id) {
        if (messageMapper.selectById(id) == null) {
            throw new MessageNotFoundException();
        }
        messageMapper.updateIsDeleteTrue(id);
    }

    @Override
    public void unWithdrawById(int id) {
        if (messageMapper.selectById(id) == null) {
            throw new MessageNotFoundException();
        }
        messageMapper.updateIsDeleteFalse(id);
    }

    @Override
    public void updateById(Message message) {
        if (messageMapper.selectById(message.getId()) == null) {
            throw new MessageNotFoundException();
        }
        if (Objects.equals(message.getDetail(), "") || Objects.equals(message.getDetail(), null)) {
            throw new EmptyMessageException();
        }
        messageMapper.updateById(message);
    }

    @Override
    public void create(Message message) {
        if (Objects.equals(message.getDetail(), "") || Objects.equals(message.getDetail(), null)) {
            throw new EmptyMessageException();
        }
        messageMapper.insert(message);
    }
}
