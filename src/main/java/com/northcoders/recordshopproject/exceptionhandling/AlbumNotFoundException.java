package com.northcoders.recordshopproject.exceptionhandling;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(String message){
        super(message);
    }
}
