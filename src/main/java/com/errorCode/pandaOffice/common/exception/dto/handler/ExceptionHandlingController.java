package com.errorCode.pandaOffice.common.exception.dto.handler;

import com.errorCode.pandaOffice.common.exception.dto.response.ExceptionResponse;
import com.errorCode.pandaOffice.common.exception.type.ConflictException;
import com.errorCode.pandaOffice.common.exception.type.CustomException;
import com.errorCode.pandaOffice.common.exception.type.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), "Details about the exception");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(ex.getMessage());
        response.setTimestamp(LocalDate.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> handleConflictException(ConflictException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), "Details about the exception");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), "Details about the exception");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
