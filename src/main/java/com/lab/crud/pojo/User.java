package com.lab.crud.pojo;

import lombok.Data;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String username;
    private String gender;
    private String password;
    private String email;
    private String introduction;
    private String avatar;
    private int qq;
    private int phone;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
