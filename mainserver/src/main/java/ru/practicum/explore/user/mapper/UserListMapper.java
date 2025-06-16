package ru.practicum.explore.user.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> toDTOList(Page<User> models);

    List<UserDto> toDTOList(List<User> models);
}