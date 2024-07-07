package com.lab.crud.exception;

public class MessageIncompleteException extends Exception{
    public MessageIncompleteException() {
        super("Missing necessary information in the message");
    }
}
