package com.lab.crud.exception;

public class RegisterInfoBlankException extends Exception{
    public RegisterInfoBlankException(){
        super("User phone or password is blank");
    }
}
