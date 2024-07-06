package com.lab.crud.controller;

import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController extends ObjectController{
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {return null;}
}
