package ru.practicum.explore.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.statistics.model.UniqueHitsStatistics;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UniqueHitStatisticsRepository extends JpaRepository<UniqueHitsStatistics, Long> {

    Optional<UniqueHitsStatistics> findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByUniqueDesc(String uri, LocalDateTime start, LocalDateTime end);
}