package com.lab.crud.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Info {
    private int status;
    private String message;
    private Object data;
}
