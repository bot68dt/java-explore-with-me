package ru.practicum.explore.statistics.service;

import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface UriStatisticsService {
    Collection<HitStatisticsDto> getUriStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

    UriStatisticsDtoWithHits addUriStatistics(UriStatisticsDto uriStatistics);
}