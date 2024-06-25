package com.lab.crud.service;

import com.lab.crud.pojo.Message;

import java.util.List;

public interface MessageService {
    List<Message> findList();
    Message findById(int id);
    void deleteById(int id);
    void updateById(Message message);
    void withdrawById(int id);
    void unWithdrawById(int id);
    void create(Message message);
}
