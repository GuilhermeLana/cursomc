package com.guilherme.cursomc.services.exceptions;

public class ObjectNotFountException extends RuntimeException {

    public ObjectNotFountException(String msg){
        super(msg);
    }

    public ObjectNotFountException(String msg, Throwable cause){
        super(msg, cause);
    }
}
