package com.emrz.inventory.exceptions;

public class GeneralErrorException extends RuntimeException{
    private String message;

    public GeneralErrorException(String message){
        super(message);
        this.message = message;
    }


    public String getMessage(){
        return this.message;
    }
}
