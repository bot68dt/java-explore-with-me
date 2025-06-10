package ru.practicum.explore.global.controller;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explore.global.dto.ErrorMessage;
import ru.practicum.explore.library.exception.*;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorMessage> handleInternalServerException(final RuntimeException e) {
        log.warn("Encountered {}: returning 500 Internal Server Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage("Internal server error.", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(final ConstraintViolationException e) {
        log.warn("Encountered {}: returning 400 ConstraintViolationException. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage("Constraint Violation Exception.", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}