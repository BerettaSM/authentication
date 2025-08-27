package com.ramon.authentication.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ramon.authentication.domain.dto.CustomError;
import com.ramon.authentication.exceptions.ApplicationException;
import com.ramon.authentication.utils.PathUtils;

@ControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomError> catchAll(ApplicationException e) {
        CustomError err = CustomError.from(e);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomError> invalidPath(NoResourceFoundException e) {
        CustomError err = new CustomError(
            HttpStatus.NOT_FOUND.value(), 
            "Invalid path", 
            Instant.now(), 
            PathUtils.getCurrentPath());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

}
