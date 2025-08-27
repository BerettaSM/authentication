package com.ramon.authentication.domain.dto;

import java.time.Instant;

import com.ramon.authentication.exceptions.ApplicationException;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomError {
    
    private Integer status;
    private String message;
    private Instant timestamp;
    private String path;

    public CustomError(ApplicationException e) {
        status = e.getHttpStatus().value();
        message = e.getMessage();
        timestamp = Instant.now();
        path = e.getPath();
    }

    public static CustomError from(ApplicationException e) {
        return new CustomError(e);
    }

}
