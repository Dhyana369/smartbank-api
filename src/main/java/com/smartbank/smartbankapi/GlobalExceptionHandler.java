package com.smartbank.smartbankapi;

import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        List<String> errors = new ArrayList<>();

        for (FieldError item : ex.getBindingResult().getFieldErrors()) {
            errors.add(item.getDefaultMessage());
        }
        errorResponse.setErrors(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(List.of(ex.getMessage()));
        return errorResponse;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientBalance(
            InsufficientBalanceException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(List.of(ex.getMessage()));

        return errorResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(List.of(ex.getMessage()));

        return ResponseEntity.badRequest().body(errorResponse);
    }
}