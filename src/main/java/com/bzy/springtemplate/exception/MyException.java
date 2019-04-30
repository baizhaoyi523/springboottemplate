package com.bzy.springtemplate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class MyException extends RuntimeException {
    private HttpStatus status;
    private String message;
}
