package ru.practicum.explore.global.controller;

//import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explore.global.dto.ErrorMessage;
import ru.practicum.explore.statistics.exception.*;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorMessage> handleInternalServerException(final RuntimeException e) {
        log.warn("Encountered {}: returning 500 Internal Server Error. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage("Internal server error.", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    /*@ExceptionHandler({UserCreationException.class, ItemCreationException.class, InvalidDateTimeException.class, ItemUnavailableException.class,
            UserValidationException.class, ItemValidationException.class, OwnerException.class})
    public ResponseEntity<ErrorMessage> handleUserCreationException(final BadRequestException e) {
        log.warn("Encountered {}: returning 400 Bad Request. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), e.getCreationMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleItemFieldException(final ConstraintViolationException e) {
        log.warn("Encountered {}: returning 400 Bad Request. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getClass().getSimpleName(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }*/

    /*@ExceptionHandler(UserEmailException.class)
    public ResponseEntity<ErrorMessage> handleUserEmailException(final UserEmailException e) {
        log.warn("Encountered {}: returning 409 conflict. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), e.getCreationMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }*/

    /*@ExceptionHandler({UserNotFoundException.class, BookingNotFoundException.class, ItemNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(final NotFoundException e) {
        log.warn("Encountered {}: returning 404 Not Found. Message: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), String.format("%s with ID %d not found", e.getGetEntityType(), e.getId()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }*/

}