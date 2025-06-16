package ru.practicum.explore.category.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import ru.practicum.explore.category.dto.CategoryDtoWithId;
import ru.practicum.explore.category.model.Category;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CategoryListMapper {
    List<CategoryDtoWithId> toDTOList(Page<Category> models);
}