package com.northcoders.recordshopproject.exceptionhandling;

public class InvalidInputsException extends RuntimeException{
    public InvalidInputsException(String message){
        super(message);
    }
}
