package com.marsamaroc.gestionengins.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("DNF",new Date(), ex.getMessage(), request.getDescription(false),HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConducteurNotDisponibleException.class)
    public ResponseEntity<?> conducteurNotDisponibleException(ConducteurNotDisponibleException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("CND",new Date(), ex.getMessage(), request.getDescription(false),HttpStatus.EXPECTATION_FAILED.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(EnginNotDisponibleException.class)
    public ResponseEntity<?> enginNotDisponibleException(EnginNotDisponibleException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("END",new Date(), ex.getMessage(), request.getDescription(false),HttpStatus.EXPECTATION_FAILED.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(NullParamException.class)
    public ResponseEntity<?> nullParamException(NullParamException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("NP",new Date(), ex.getMessage(), request.getDescription(false),HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(AffectDemandDeleteException.class)
    public ResponseEntity<?> affectDemandeException(AffectDemandDeleteException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("ADD",new Date(), ex.getMessage(), request.getDescription(false),HttpStatus.EXPECTATION_FAILED.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(EnginSortieException.class)
    public ResponseEntity<?> enginSortieException(EnginSortieException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("ES",new Date(), ex.getMessage(), request.getDescription(false),HttpStatus.EXPECTATION_FAILED.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails("IA",new Date(), "Illegal Argument exception", request.getDescription(false),HttpStatus.EXPECTATION_FAILED.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.EXPECTATION_FAILED);
    }

}
