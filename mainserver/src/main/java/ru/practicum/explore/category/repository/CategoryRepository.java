package ru.practicum.explore.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.category.model.Category;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(long id);

    Optional<Category> findByName(String name);
}