package com.lab.crud.pojo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Info {
    private final String error;
    private final HttpStatus status;

    public Info(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }
}
