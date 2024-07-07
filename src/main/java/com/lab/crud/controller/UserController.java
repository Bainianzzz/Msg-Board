package com.lab.crud.controller;

import com.lab.crud.exception.UserNotFoundException;
import com.lab.crud.pojo.Info;
import com.lab.crud.pojo.User;
import com.lab.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends ObjectController{
    @Autowired
    private UserService userService;

    //查看用户信息
    @GetMapping("/{id}")
    public ResponseEntity<Info> getUser(@PathVariable String id) {
        try {
            User user = userService.getUserById(Integer.parseInt(id));
            log.info("getUser: {}", user.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Info(20003, "success", user));
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
            log.info("updateUser: {}", user.getId());
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
            log.info("deleteUser: {}", id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Info(20005, "success", null));
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
