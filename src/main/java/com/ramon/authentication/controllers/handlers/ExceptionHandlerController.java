package com.ramon.authentication.controllers.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ramon.authentication.domain.dto.CustomError;
import com.ramon.authentication.exceptions.ApplicationException;

@ControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomError> catchAll(ApplicationException e) {
        CustomError err = CustomError.from(e);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

}
