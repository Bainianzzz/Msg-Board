package com.lab.crud.controller;

import com.lab.crud.exception.MessageEmptyException;
import com.lab.crud.exception.MessageIncompleteException;
import com.lab.crud.exception.MessageNotFoundException;
import com.lab.crud.exception.UserNotFoundException;
import com.lab.crud.pojo.Info;
import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
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
            if (!messages.isEmpty()) {
                log.info("get message by id: {}", id);
                return success(messages, 20006);
            } else return error(new MessageNotFoundException(), HttpStatus.NOT_FOUND, 40402);
        } catch (NumberFormatException e) {
            return error(e, HttpStatus.BAD_REQUEST, 40001);
        }
    }

    //获取用户的所有留言
    @GetMapping("/user/{uid}")
    public ResponseEntity<Info> getMessageByUserId(@PathVariable String uid, String page) {
        try {
            List<Message> messages = messageService.getMessagesByUid(Integer.parseInt(uid), Integer.parseInt(page));
            log.info("get all messages from user {}", uid);
            return success(messages, 20007);
        } catch (NumberFormatException e) {
            return error(e, HttpStatus.BAD_REQUEST, 40001);
        } catch (MessageNotFoundException e) {
            return error(e, HttpStatus.NOT_FOUND, 40402);
        }
    }

    //发布留言或者回复留言
    @PostMapping
    public ResponseEntity<Info> addMessage(@RequestBody Message message) {
        try {
            messageService.addMessage(message);
            log.info("{} add message", message.getUid());
            return success(null, 20008);
        } catch (UserNotFoundException e) {
            return error(e, HttpStatus.NOT_FOUND, 40401);
        } catch (MessageNotFoundException e) {
            return error(e, HttpStatus.NOT_FOUND, 40402);
        }catch (NullPointerException e){
            return error(new MessageIncompleteException(), HttpStatus.BAD_REQUEST, 40003);
        } catch (MessageEmptyException e) {
            return error(e, HttpStatus.BAD_REQUEST, 40002);
        }
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
