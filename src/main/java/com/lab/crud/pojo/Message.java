package com.lab.crud.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Integer id;
    private Integer userId;
    private Integer parentId;
    private String detail;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean isDelete;
}
