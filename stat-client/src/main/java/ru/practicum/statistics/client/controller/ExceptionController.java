package ru.practicum.statistics.client.controller;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.statistics.client.dto.ErrorMessage;
import ru.practicum.statistics.client.exception.InternalServerException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({InternalServerException.class, ResourceAccessException.class, HttpHostConnectException.class})
    public ResponseEntity<ErrorMessage> handleInternalServerException(final RuntimeException e) {
        log.warn("Encountered {}: returning 500 Internal Server Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getCause().getMessage()), e.getLocalizedMessage(), e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler({HttpClientErrorException.BadRequest.class, MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorMessage> handleBadRequest(final RuntimeException e) {
        log.warn("Encountered {}: returning 400 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getCause().getMessage()), e.getLocalizedMessage(), e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Encountered {}: returning 400 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getBody().getTitle()), e.getLocalizedMessage(), e.getBody().getDetail());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleBad(final DataIntegrityViolationException e) {
        log.warn("Encountered {}: returning 400 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getCause().getMessage()), e.getLocalizedMessage(), e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<ErrorMessage> handleConflict(final RuntimeException e) {
        log.warn("Encountered {}: returning 409 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getCause().getMessage()), e.getLocalizedMessage(), e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(final RuntimeException e) {
        log.warn("Encountered {}: returning 404 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getCause().getMessage()), e.getLocalizedMessage(), e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}