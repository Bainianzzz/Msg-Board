package com.lab.crud.controller;

import com.lab.crud.pojo.Info;
import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController extends ObjectController {
    @Autowired
    private MessageService messageService;

    //获取单条留言及其所有回复
    @GetMapping("/{id}/{page}")
    public ResponseEntity<Info> getMessagesById(@PathVariable String id, @PathVariable String page) {
        return null;
    }

    //获取用户的所有留言
    @GetMapping("/user/{page}")
    public ResponseEntity<Info> getMessageByUserId(HttpServletRequest request, @PathVariable String page) {
        return null;
    }

    //发布留言或者回复留言
    @PostMapping
    public ResponseEntity<Info> addMessage(@RequestBody Message message) {
        return null;
    }

    //修改留言信息或者撤回留言（软删除）
    @PutMapping("/{id}")
    public ResponseEntity<Info> updateMessage(@RequestBody Message message) {
        return null;
    }

    //硬删除留言
    @DeleteMapping("/{id}")
    public ResponseEntity<Info> deleteMessage(@PathVariable String id) {
        return null;
    }
}
