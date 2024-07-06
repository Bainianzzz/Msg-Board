package com.lab.crud.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(){
        super("user not found");
    }
}
