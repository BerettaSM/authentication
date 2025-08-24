package com.ramon.authentication.security.exceptions;

import org.springframework.http.HttpStatus;

import com.ramon.authentication.exceptions.ApplicationException;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }

    public UnauthorizedException(String message, Throwable cause) {
        this(message, cause, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public UnauthorizedException(Throwable cause, HttpStatus httpStatus) {
        this(cause.getMessage(), cause, httpStatus);
    }

    public UnauthorizedException(String message) {
        this(message, (Throwable) null);
    }

    public UnauthorizedException() {
        this("Unauthorized");
    }

}
