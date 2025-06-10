package ru.practicum.explore.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.statistics.model.Statistics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UriStatisticsRepository extends JpaRepository<Statistics, Long> {

    Optional<Statistics> findFirst1ByUriAndAppAndIpOrderByHitsDesc(String uri, String app, String ip);

    Optional<Statistics> findFirst1ByUriAndAppOrderByHitsDesc(String uri, String app);

    @Query(value = " select distinct (s.uri), s.app, count(s.uri) from Statistics s where (s.timestamp >= ?1 and s.timestamp <= ?2) group by distinct (s.uri), s.app ")
    List<Object[]> findAllUris(LocalDateTime start, LocalDateTime end);

    @Query(value = " select distinct (s.uri), s.app, count(distinct s.ip) from Statistics s where (s.timestamp >= ?1 and s.timestamp <= ?2) group by distinct (s.uri), s.app ")
    List<Object[]> findAllUniqueUris(LocalDateTime start, LocalDateTime end);

    @Query(value = " select s.uri, s.app, count(s.uri) from Statistics s where (s.timestamp >= ?1 and s.timestamp <= ?2 and s.uri = ?3) group by s.uri, s.app ")
    Optional<List<Object[]>> findMentionedUris(LocalDateTime start, LocalDateTime end, String uri);

    @Query(value = " select s.uri, s.app, count(distinct s.ip) from Statistics s where (s.timestamp >= ?1 and s.timestamp <= ?2 and s.uri = ?3) group by s.uri, s.app ")
    Optional<List<Object[]>> findMentionedUniqueUris(LocalDateTime start, LocalDateTime end, String uri);
}