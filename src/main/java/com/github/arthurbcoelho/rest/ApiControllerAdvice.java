package com.github.arthurbcoelho.rest;

import com.github.arthurbcoelho.exceptions.LivroEmprestadoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrors handleValidationException(ValidationException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleEntityNotFoundException(EntityNotFoundException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(LivroEmprestadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiErrors handleLivroEmprestadoException(LivroEmprestadoException e) {
        return new ApiErrors(e.getMessage());
    }
}
