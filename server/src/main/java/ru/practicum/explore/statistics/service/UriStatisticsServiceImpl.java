package ru.practicum.explore.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;
import ru.practicum.explore.statistics.mapper.UriStatisticsMapperNew;
import ru.practicum.explore.statistics.model.HitsStatistics;
import ru.practicum.explore.statistics.model.Statistics;
import ru.practicum.explore.statistics.model.UniqueHitsStatistics;
import ru.practicum.explore.statistics.repository.HitStatisticsRepository;
import ru.practicum.explore.statistics.repository.UniqueHitStatisticsRepository;
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
    private final HitStatisticsRepository hitStatisticsRepository;
    private final UniqueHitStatisticsRepository uniqueHitStatisticsRepository;

    @Override
    public List<HitStatisticsDto> getUriStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<HitStatisticsDto> collection = new ArrayList<>();
        if (uris.isEmpty()) {
            uris = uriStatisticsRepository.findAllUris(start, end);
            if (unique) {
                for (String uri : uris) {
                    collection.add(UriStatisticsMapperNew.mapToUniqueHitStatistics(uniqueHitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByUniqueDesc(uri, start, end).get()));
                }
            } else {
                for (String uri : uris) {
                    collection.add(UriStatisticsMapperNew.mapToHitStatistics(hitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByHitsDesc(uri, start, end).get()));
                }
            }
            collection.sort(Comparator.comparing(HitStatisticsDto::getHits).reversed());
            return collection;
        }
        Optional<UniqueHitsStatistics> checkUniqueHits;
        Optional<HitsStatistics> chekHits;
        for (String uri : uris) {
            if (unique) {
                checkUniqueHits = uniqueHitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByUniqueDesc(uri, start, end);
                checkUniqueHits.ifPresent(uniqueHitsStatistics -> collection.add(UriStatisticsMapperNew.mapToUniqueHitStatistics(uniqueHitsStatistics)));
            } else {
                chekHits = hitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByHitsDesc(uri, start, end);
                chekHits.ifPresent(uniqueHitsStatistics -> collection.add(UriStatisticsMapperNew.mapToHitStatistics(uniqueHitsStatistics)));
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