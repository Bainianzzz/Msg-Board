package com.lab.crud.pojo;

import lombok.Data;

@Data
public class Message {
    private Integer id;
    private Integer uid;
    private Integer pid;
    private String detail;
}
