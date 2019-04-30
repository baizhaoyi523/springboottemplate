package com.bzy.springtemplate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionResource> handle(MyException exception){
        log.info("Exception occurred.", exception);
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionResource(exception.getMessage()));
    }

}
