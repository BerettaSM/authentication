package com.ramon.authentication.services.exceptions;

import org.springframework.http.HttpStatus;

import com.ramon.authentication.exceptions.ApplicationException;

public class DatabaseException extends ApplicationException {

     public DatabaseException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public DatabaseException(String message, Throwable cause) {
        this(message, cause, HttpStatus.BAD_REQUEST);
    }

    public DatabaseException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public DatabaseException(Throwable cause, HttpStatus httpStatus) {
        this(cause.getMessage(), cause, httpStatus);
    }

    public DatabaseException(String message) {
        this(message, (Throwable) null);
    }

    public DatabaseException() {
        this("Error occurred in database");
    }

}
