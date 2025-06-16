package ru.practicum.explore.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore.category.dto.CategoryDtoWithId;
import ru.practicum.explore.category.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDtoWithId toDTO(Category model);
}