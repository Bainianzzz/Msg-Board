package com.lab.crud.exception;

public class MessageEmptyException extends Exception{
    public MessageEmptyException() {
        super("The detail of message is empty");
    }
}
