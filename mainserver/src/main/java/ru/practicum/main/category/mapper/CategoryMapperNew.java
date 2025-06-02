package ru.practicum.main.category.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.CategoryDtoWithId;
import ru.practicum.main.category.model.Category;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapperNew {

    public static CategoryDtoWithId mapToCategoryDtoWithId(Category category) {
        return new CategoryDtoWithId(category.getId(), category.getName());
    }

    public static List<CategoryDtoWithId> mapToCategoryDtoWithId(Iterable<Category> categories) {
        List<CategoryDtoWithId> result = new ArrayList<>();

        for (Category category : categories) {
            result.add(mapToCategoryDtoWithId(category));
        }

        return result;
    }

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