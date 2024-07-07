package com.lab.crud.controller;

import com.lab.crud.pojo.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ObjectController {
    protected final Logger log = LoggerFactory.getLogger(AuthController.class);

    protected ResponseEntity<Info> error(Exception e, HttpStatus status, int code) {
        log.error(e.getMessage());
        return ResponseEntity.status(status).
                body(new Info(code, e.getMessage(), null));
    }

    protected ResponseEntity<Info> success(Object data, int code) {
        return ResponseEntity.status(HttpStatus.OK).body(new Info(code, "success", data));
    }
}
