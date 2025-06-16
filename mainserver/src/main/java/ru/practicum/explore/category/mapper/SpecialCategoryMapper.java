package ru.practicum.explore.category.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.model.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecialCategoryMapper {
    public static Category mapToCategory(Category changeable, CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(changeable.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static Category mapToCategory(CategoryDto changeable) {
        Category category = new Category();
        category.setName(changeable.getName());
        return category;
    }
}