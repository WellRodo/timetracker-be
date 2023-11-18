package miniapp.timetracker.controller;

import lombok.extern.slf4j.Slf4j;
import miniapp.timetracker.model.contracts.CustomException;
import miniapp.timetracker.model.contracts.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handle(CustomException exception){
        return ResponseEntity.status(exception.getStatusCode()).body(new Message(exception.getMessage(), (HttpStatus) exception.getStatusCode()));
    }
}
