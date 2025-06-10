package ru.practicum.explore.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.library.model.AdLocation;

@Repository
public interface AdLocationRepository extends JpaRepository<AdLocation, Long> {
}