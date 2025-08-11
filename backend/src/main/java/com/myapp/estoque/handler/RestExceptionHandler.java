package com.myapp.estoque.handler;

import com.myapp.estoque.exception.EmptyObjectException;
import com.myapp.estoque.exception.NotEnoughException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmptyObjectException.class)
    public ResponseEntity<ErrorMessage> objectNotFound(EmptyObjectException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(NotEnoughException.class)
    private ResponseEntity<ErrorMessage> notEnoughQuantity(NotEnoughException exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INSUFFICIENT_STORAGE, "Quantidade disponível no estoque é menor.");
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(errorMessage);
    }
}
