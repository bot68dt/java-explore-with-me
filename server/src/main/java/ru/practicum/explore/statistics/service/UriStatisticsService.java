package ru.practicum.explore.statistics.service;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;

import java.time.LocalDateTime;
import java.util.List;

public interface UriStatisticsService {
    List<HitStatisticsDto> getDecodedUriStatistics(@PastOrPresent LocalDateTime start, @FutureOrPresent LocalDateTime end, List<String> uris, boolean unique);

    UriStatisticsDtoWithHits addUriStatistics(UriStatisticsDto uriStatistics);
}