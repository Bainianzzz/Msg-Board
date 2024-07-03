package com.lab.crud.service.Impl;

import com.lab.crud.mapper.MessageMapper;
import com.lab.crud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
}
