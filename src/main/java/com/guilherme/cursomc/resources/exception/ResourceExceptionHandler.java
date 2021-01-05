package com.guilherme.cursomc.resources.exception;

import com.guilherme.cursomc.services.exceptions.DataIntegrityException;
import com.guilherme.cursomc.services.exceptions.ObjectNotFountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFountException.class)
    public ResponseEntity<StantardError> objectNotFound(ObjectNotFountException e, HttpServletRequest request){

        StantardError err = new StantardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StantardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){

        StantardError err = new StantardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
