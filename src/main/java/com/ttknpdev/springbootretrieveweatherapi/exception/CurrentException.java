package com.ttknpdev.springbootretrieveweatherapi.exception;

import com.ttknpdev.springbootretrieveweatherapi.exception.entity.Warning;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CurrentException extends Throwable {
    @ExceptionHandler
    public ResponseEntity<Warning> handlerException(Exception exception) {
        Warning warning = new Warning((short)HttpStatus.BAD_REQUEST.value() , exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(warning);
    }

}
