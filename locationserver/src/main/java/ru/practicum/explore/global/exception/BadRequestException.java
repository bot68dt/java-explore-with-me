package ru.practicum.explore.global.exception;

import lombok.Getter;

@Getter
public abstract class BadRequestException extends RuntimeException {

    String message;
    String creationMessage;

    Class<?> getEntityType;
}