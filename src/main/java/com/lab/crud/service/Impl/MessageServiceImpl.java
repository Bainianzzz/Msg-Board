package com.lab.crud.service.Impl;

import com.lab.crud.exception.MessageNotFoundException;
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
    //递归查询出某条留言及其所有子留言
    public void getMessagesById(int id,List<Message> messages){
        Message message = messageMapper.selectMessageById(id);
        if (message == null) return;

        messages.add(message);

        Message childMessage = messageMapper.selectMessageByPid(message.getId());
        if (childMessage == null) return;
        getMessagesById(childMessage.getId(),messages);
    }

    @Override
    public List<Message> getMessagesByUid(int pid, int page) {
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
