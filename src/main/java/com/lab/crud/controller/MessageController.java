package com.lab.crud.controller;

import com.lab.crud.exception.MessageNotFoundException;
import com.lab.crud.pojo.Info;
import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController extends ObjectController {
    @Autowired
    private MessageService messageService;

    //获取单条留言及其所有回复
    @GetMapping("/{id}")
    public ResponseEntity<Info> getMessagesById(@PathVariable String id) {
        try {
            List<Message> messages = new ArrayList<>();
            messageService.getMessagesById(Integer.parseInt(id), messages);
            if (!messages.isEmpty()) return ResponseEntity.status(HttpStatus.OK).
                    body(new Info(20006, "success", messages));
            else {
                log.error("Message Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body(new Info(40402, "Message Not Found", null));
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new Info(40001, e.getMessage(), null));
        }
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
