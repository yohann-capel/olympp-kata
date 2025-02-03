package com.kata.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomlExceptionHandler {

    @ExceptionHandler(IllegalAmountException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleIllegalAmountException(IllegalAmountException ex) {
        return ex.getMessage();
    }


    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIllegalAmountException(AccountNotFoundException ex) {
        return ex.getMessage();
    }

}
