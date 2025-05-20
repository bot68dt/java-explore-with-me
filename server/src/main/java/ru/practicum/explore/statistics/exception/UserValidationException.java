/*package ru.practicum.explore.statistics.exception;

import lombok.Getter;
import ru.practicum.explore.global.exception.BadRequestException;
import ru.practicum.explore.statistics.model.UriStatistics;

@Getter
public class UserValidationException extends BadRequestException {

    private final String message;
    private final String validationMessage;

    public UserValidationException(String message, String validationMessage) {
        this.message = message;
        this.validationMessage = validationMessage;
    }

    public Class<?> getEntityType() {
        return UriStatistics.class;
    }
}*/