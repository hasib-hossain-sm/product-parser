package com.hasib.ProductParser.exception;

import com.hasib.ProductParser.dto.ApiErrorResponse;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.EmptyFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                "An unexpected error occurred.",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                "Invalid argument provided. Please check the input and try again.",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        ), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CsvValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleCsvValidationException(CsvValidationException e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                "CSV file validation failed. Please ensure the file is correctly formatted and try again.",
                e.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        ), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiErrorResponse> handleIOException(IOException e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                "An error occurred while processing. Please try again.",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                "A required value was not found. Please check the input and try again.",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        ), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ApiErrorResponse> handleEmptyFileException(EmptyFileException e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                "The uploaded file is empty. Please provide a valid file.",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        ), HttpStatus.BAD_REQUEST);
    }

}
