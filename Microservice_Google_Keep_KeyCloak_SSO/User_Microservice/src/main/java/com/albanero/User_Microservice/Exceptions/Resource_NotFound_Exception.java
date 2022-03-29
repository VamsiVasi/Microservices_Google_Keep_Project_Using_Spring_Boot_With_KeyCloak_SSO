package com.albanero.User_Microservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class Resource_NotFound_Exception extends Exception{

    private static final long serialVersionUID = 1L;

    public Resource_NotFound_Exception(String message){
        super(message);
    }
}