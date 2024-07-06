package com.lab.crud.controller;

import com.lab.crud.exception.RegisterInfoBlankException;
import com.lab.crud.exception.UserNotFoundException;
import com.lab.crud.pojo.Info;
import com.lab.crud.pojo.User;
import com.lab.crud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    //查看用户信息
    @GetMapping("/{id}")
    public ResponseEntity<Info> getUser(@PathVariable String id) {
        try {
            User user = userService.getUserById(Integer.parseInt(id));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Info(20002, "success", user));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new Info(40401, e.getMessage(), null));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new Info(40001, e.getMessage(), null));
        }
    }

    //更新用户信息
    @PutMapping("/{id}")
    public ResponseEntity<Info> updateUser(@RequestBody User user, @PathVariable String id) {
        try {
            if (user.getId() <= 0) {
                user.setId(Integer.parseInt(id));
            }
            userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Info(20004, "success", null));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new Info(40401, e.getMessage(), null));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new Info(40001, e.getMessage(), null));
        }
    }

    //注销用户
    @DeleteMapping("/{id}")
    public ResponseEntity<Info> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUserById(Integer.parseInt(id));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Info(20004, "success", null));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new Info(40401, e.getMessage(), null));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new Info(40001, e.getMessage(), null));
        }
    }
}
