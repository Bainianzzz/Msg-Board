package com.lab.crud.controller;

import com.lab.crud.pojo.Info;
import com.lab.crud.exception.EmptyMessageException;
import com.lab.crud.exception.MessageNotFoundException;
import com.lab.crud.pojo.Message;
import com.lab.crud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> createMessages(@RequestBody Message message) {
        try {
            messageService.create(message);
            return ResponseEntity.ok(new Info("留言成功",HttpStatus.OK));
        }catch (EmptyMessageException e){
            return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable String id) {
        try {
            messageService.deleteById(Integer.parseInt(id));
            return ResponseEntity.ok(new Info("删除留言成功",HttpStatus.OK));
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
        }catch (MessageNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @PutMapping
    public ResponseEntity<?> updateMessage(@RequestBody Message message) {
        try{
            messageService.updateById(message);
            return ResponseEntity.ok(new Info("修改留言成功",HttpStatus.OK));
        }catch (EmptyMessageException e){
            return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
        }catch (MessageNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<?> withdrawMessage(@PathVariable String id) {
        try {
            messageService.withdrawById(Integer.parseInt(id));
            return ResponseEntity.ok(new Info("留言撤回成功",HttpStatus.OK));
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
        }catch (MessageNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/un_withdraw/{id}")
    public ResponseEntity<?> unWithdrawMessage(@PathVariable String id) {
        try {
            messageService.unWithdrawById(Integer.parseInt(id));
            return ResponseEntity.ok(new Info("撤回留言恢复成功",HttpStatus.OK));
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
        }catch (MessageNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.findList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable String id) {
        try {
            Message msg= messageService.findById(Integer.parseInt(id));
            return ResponseEntity.ok(msg);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new Info(e.getMessage(), HttpStatus.BAD_REQUEST));
        } catch (MessageNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
