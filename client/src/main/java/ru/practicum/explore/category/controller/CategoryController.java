package ru.practicum.explore.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.client.AdminCategoryClient;
import ru.practicum.explore.category.client.CategoryClient;
import ru.practicum.explore.category.dto.CategoryDto;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryController {

    private final CategoryClient categoryClient;
    private final AdminCategoryClient adminCategoryClient;

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Object> getCategory(@Valid @PathVariable long catId) {
        log.info("Request to get category with ID {} received.", catId);
        return categoryClient.getCategory(catId);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategories(@Valid @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get all categories received.");
        return categoryClient.getAllCategories(from, size);
    }

    @PatchMapping("/admin/categories/{catId}")
    public ResponseEntity<Object> changeCategory(@Valid @PathVariable long catId, @Valid @RequestBody CategoryDto categoryDto) {
        log.info("Request to change category {} received.", categoryDto);
        return adminCategoryClient.changeCategory(catId, categoryDto);
    }

    @DeleteMapping("/admin/categories/{catId}")
    public ResponseEntity<Object> deleteCategory(@Valid @PathVariable long catId) {
        log.info("Request to delete category with ID {} received.", catId);
        return adminCategoryClient.deleteCategory(catId);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Request to create new category received: {}", categoryDto);
        return adminCategoryClient.createCategory(categoryDto);
    }
}