package com.lab.crud.exception;

public class EmptyMessageException extends RuntimeException {
    public EmptyMessageException() {
        super("Message should not be empty");
    }
}
