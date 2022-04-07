package com.marsamaroc.gestionengins.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConducteurNotDisponibleException extends Exception{

    private static final long serialVersionUID = 1L;

    public ConducteurNotDisponibleException(String message){
        super(message);
    }
}
