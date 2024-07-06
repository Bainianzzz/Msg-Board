package com.lab.crud.exception;

public class TokenDyingException extends RuntimeException{
    public TokenDyingException(){
        super("Token's expiration is shorter than 5 minutes");
    }
}
