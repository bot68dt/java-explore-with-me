package ru.practicum.explore.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.statistics.model.Statistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UriStatisticsRepository extends JpaRepository<Statistics, Long> {

    Optional<Statistics> findFirst1ByUriAndAppAndIpOrderByTimeDesc(String uri, String app, String ip);

    Optional<Statistics> findFirst1ByUriAndAppOrderByTimeDesc(String uri, String app);

    @Query(value = " select distinct (s.uri) from Statistics s where (s.time >= ?1 and s.time <= ?2) group by s.uri ")
    List<String> findAllUris(LocalDateTime start, LocalDateTime end);
}