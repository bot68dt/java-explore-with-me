package ru.practicum.main.global.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import ru.practicum.main.global.dto.ErrorMessage;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(final EntityNotFoundException e) {
        log.warn("Encountered {}: returning 404 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), "Entity not found in DB"), e.getLocalizedMessage(), "Entity not found in DB");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleBadEntity(final DataIntegrityViolationException e) {
        log.warn("Encountered {}: returning 400 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), e.getCause().getMessage()), e.getLocalizedMessage(), e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage> handleConflict(final HttpClientErrorException e) {
        log.warn("Encountered {}: returning 409 Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(List.of(e.getClass().getSimpleName(), "Data integrity violation"), e.getLocalizedMessage(), "Data integrity violation");
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}