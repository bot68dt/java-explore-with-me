package ru.practicum.explore.global.exception;

import lombok.Getter;

@Getter
public abstract class NotFoundException extends RuntimeException {

    String message;
    long id;

    Class<?> getEntityType;
}