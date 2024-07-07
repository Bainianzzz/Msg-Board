package com.lab.crud.exception;

public class PermissonDeniedException extends Exception{
    public PermissonDeniedException(){
        super("you don't have permission to modify or delete others' messages");
    }
}
