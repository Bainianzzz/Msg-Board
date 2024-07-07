package com.lab.crud.exception;

public class MessageNotFoundException extends Exception{
    public MessageNotFoundException(){
        super("Message Not Found");
    }
}
