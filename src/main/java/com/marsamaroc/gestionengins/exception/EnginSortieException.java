package com.marsamaroc.gestionengins.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EnginSortieException extends Exception{

    private static final long serialVersionUID = 1L;

    public EnginSortieException(String message){
        super(message);
    }
}
