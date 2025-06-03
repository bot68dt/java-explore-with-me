package ru.practicum.main.category.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.CategoryDtoWithId;
import ru.practicum.main.category.mapper.CategoryMapperNew;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDtoWithId getCategory(long catId) {
        Optional<Category> category = categoryRepository.findById(catId);
        if (category.isPresent()) return CategoryMapperNew.mapToCategoryDtoWithId(category.get());
        else throw new EntityNotFoundException();
    }

    @Override
    public Collection<CategoryDtoWithId> getAllCategories(Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return CategoryMapperNew.mapToCategoryDtoWithId(categoryRepository.findAll(page));
    }

    @Override
    @Transactional
    public CategoryDtoWithId changeCategory(long catId, CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(catId);
        Optional<Category> name = categoryRepository.findByName(categoryDto.getName());
        if (name.isPresent()) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        if (category.isPresent())
            return CategoryMapperNew.mapToCategoryDtoWithId(categoryRepository.saveAndFlush(CategoryMapperNew.mapToCategory(category.get(), categoryDto)));
        else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        Optional<Event> event = eventRepository.findByCategoryId(catId);
        if (event.isPresent()) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        else categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDtoWithId createCategory(CategoryDto categoryDto) {
        Optional<Category> name = categoryRepository.findByName(categoryDto.getName());
        if (name.isPresent()) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        return CategoryMapperNew.mapToCategoryDtoWithId(categoryRepository.saveAndFlush(CategoryMapperNew.mapToCategory(categoryDto)));
    }
}