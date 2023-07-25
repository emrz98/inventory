package com.emrz.inventory.exceptionhandler;

import com.emrz.inventory.dto.response.ErrorResponse;
import com.emrz.inventory.exceptions.GeneralErrorException;
import com.emrz.inventory.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralErrorExceptionHandler {

    @ExceptionHandler(GeneralErrorException.class)
    public ResponseEntity<ErrorResponse> handler(GeneralErrorException generalErrorException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(generalErrorException.getMessage());
        errorResponse.setCode(1);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(ProductNotFoundException productNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(productNotFoundException.getMessage());
        errorResponse.setCode(1);
        return ResponseEntity.notFound().build();
    }
}
