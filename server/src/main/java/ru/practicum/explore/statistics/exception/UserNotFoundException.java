/*package ru.practicum.explore.statistics.exception;

import lombok.Getter;
import ru.practicum.explore.global.exception.NotFoundException;
import ru.practicum.explore.statistics.model.UriStatistics;

@Getter
public class UserNotFoundException extends NotFoundException {

  private final String message;
  private final long id;

  public UserNotFoundException(String message, long id) {
    this.message = message;
    this.id = id;
  }

  public Class<?> getEntityType() {
    return UriStatistics.class;
  }
}*/