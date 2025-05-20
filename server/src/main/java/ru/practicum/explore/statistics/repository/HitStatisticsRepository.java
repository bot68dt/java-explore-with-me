package ru.practicum.explore.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.statistics.model.HitsStatistics;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface HitStatisticsRepository extends JpaRepository<HitsStatistics, Long> {

    Optional<HitsStatistics> findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByHitsDesc(String uri, LocalDateTime start, LocalDateTime end);
}