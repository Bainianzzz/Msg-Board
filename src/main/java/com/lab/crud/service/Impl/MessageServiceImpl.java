package com.lab.crud.service.Impl;

import com.lab.crud.exception.MessageEmptyException;
import com.lab.crud.exception.MessageNotFoundException;
import com.lab.crud.exception.UserNotFoundException;
import com.lab.crud.mapper.MessageMapper;
import com.lab.crud.mapper.UserMapper;
import com.lab.crud.pojo.Message;
import com.lab.crud.pojo.User;
import com.lab.crud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    //递归查询出某条留言及其所有子留言
    public void getMessagesById(int id, List<Message> messages) {
        Message message = messageMapper.selectMessageById(id);
        if (message == null) return;
        messages.add(message);

        Message[] childMessage = messageMapper.selectMessageByPid(message.getId());
        if (childMessage == null) return;
        for (Message child : childMessage) {
            getMessagesById(child.getId(), messages);
        }
    }

    @Override
    public List<Message> getMessagesByUid(int uid, int page) throws MessageNotFoundException {
        int begin = (page - 1) * page;
        int end = page + 10;
        List<Message> messages = messageMapper.selectMessagesByUId(uid, begin, end);
        if (messages == null) throw new MessageNotFoundException();
        return messages;
    }

    @Override
    public void addMessage(Message message) throws MessageEmptyException, UserNotFoundException, MessageNotFoundException {
        if (message.getDetail().isBlank()) throw new MessageEmptyException();
        User user = userMapper.getUserById(message.getUid());
        if (user == null) throw new UserNotFoundException();
        Message parentMessage = messageMapper.selectMessageById(message.getPid());
        if (parentMessage == null) throw new MessageNotFoundException();
        messageMapper.insertMessage(message);
    }

    @Override
    public void updateMessage(Message message) throws MessageEmptyException {
        if (messageMapper.selectMessageById(message.getId()) == null) throw new MessageEmptyException();
        messageMapper.updateMessage(message);
    }

    @Override
    public void deleteMessage(int id) throws MessageEmptyException {
        if (messageMapper.selectMessageById(id) == null) throw new MessageEmptyException();
        messageMapper.deleteMessageById(id);
    }
}
