package ru.practicum.explore.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.statistics.dto.*;
import ru.practicum.explore.statistics.mapper.UriStatisticsMapperNew;
import ru.practicum.explore.statistics.model.Statistics;
import ru.practicum.explore.statistics.repository.UriStatisticsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UriStatisticsServiceImpl implements UriStatisticsService {

    private final UriStatisticsRepository uriStatisticsRepository;

    @Override
    public List<HitStatisticsDto> getUriStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<HitStatisticsDto> collection = new ArrayList<>();
        if (uris.isEmpty()) {
            if (unique)
                collection.addAll(UriStatisticsMapperNew.mapToHitStatistics(uriStatisticsRepository.findAllUniqueUris(start, end)));
            else
                collection.addAll(UriStatisticsMapperNew.mapToHitStatistics(uriStatisticsRepository.findAllUris(start, end)));
            collection.sort(Comparator.comparing(HitStatisticsDto::getHits).reversed());
            return collection;
        }
        Optional<List<Object[]>> checkUniqueHits;
        Optional<List<Object[]>> chekHits;
        for (String uri : uris) {
            if (unique) {
                checkUniqueHits = uriStatisticsRepository.findMentionedUniqueUris(start, end, uri);
                checkUniqueHits.ifPresent(HitsStatistics -> collection.addAll(UriStatisticsMapperNew.mapToHitStatistics(HitsStatistics)));
            } else {
                chekHits = uriStatisticsRepository.findMentionedUris(start, end, uri);
                chekHits.ifPresent(uniqueHitsStatistics -> collection.addAll(UriStatisticsMapperNew.mapToHitStatistics(uniqueHitsStatistics)));
            }
        }
        collection.sort(Comparator.comparing(HitStatisticsDto::getHits).reversed());
        return collection;
    }

    @Override
    @Transactional
    public UriStatisticsDtoWithHits addUriStatistics(UriStatisticsDto uriStatistics) {
        Optional<Statistics> statistics = uriStatisticsRepository.findFirst1ByUriAndAppAndIpOrderByHitsDesc(uriStatistics.getUri(), uriStatistics.getApp(), uriStatistics.getIp());
        UriStatisticsDtoWithHits stat;
        if (statistics.isEmpty()) {
            statistics = uriStatisticsRepository.findFirst1ByUriAndAppOrderByHitsDesc(uriStatistics.getUri(), uriStatistics.getApp());
            if (statistics.isEmpty())
                stat = UriStatisticsMapperNew.mapToNewUriStatisticsDtoWithHits(uriStatisticsRepository.saveAndFlush(UriStatisticsMapperNew.mapToNewUriStatistics(uriStatistics, 1L, 1L)));
            else
                stat = UriStatisticsMapperNew.mapToNewUriStatisticsDtoWithHits(uriStatisticsRepository.saveAndFlush(UriStatisticsMapperNew.mapToNewUriStatistics(uriStatistics, statistics.get().getHits() + 1L, statistics.get().getUnique() + 1L)));
        } else
            stat = UriStatisticsMapperNew.mapToNewUriStatisticsDtoWithHits(uriStatisticsRepository.saveAndFlush(UriStatisticsMapperNew.mapToNewUriStatistics(uriStatistics, statistics.get().getHits() + 1L, statistics.get().getUnique())));
        return (stat);
    }
}