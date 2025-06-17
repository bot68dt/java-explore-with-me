package ru.practicum.explore.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDTO(User model);

    User toModel(UserDto dto);
}
