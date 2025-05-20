package ru.practicum.explore.statistics.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;
import ru.practicum.explore.statistics.model.HitsStatistics;
import ru.practicum.explore.statistics.model.Statistics;
import ru.practicum.explore.statistics.model.UniqueHitsStatistics;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriStatisticsMapperNew {

    public static HitStatisticsDto mapToHitStatistics(HitsStatistics uriStatistics) {
        return new HitStatisticsDto(uriStatistics.getApp(), uriStatistics.getUri(), uriStatistics.getHits());
    }

    public static HitStatisticsDto mapToUniqueHitStatistics(UniqueHitsStatistics uriStatistics) {
        return new HitStatisticsDto(uriStatistics.getApp(), uriStatistics.getUri(), uriStatistics.getUnique());
    }

    public static List<HitStatisticsDto> mapToHitStatistics(Iterable<HitsStatistics> stats) {
        List<HitStatisticsDto> result = new ArrayList<>();

        for (HitsStatistics uriStatistics : stats) {
            result.add(mapToHitStatistics(uriStatistics));
        }

        return result;
    }

    public static List<HitStatisticsDto> mapToUniqueHitStatistics(Iterable<UniqueHitsStatistics> stats) {
        List<HitStatisticsDto> result = new ArrayList<>();

        for (UniqueHitsStatistics uriStatistics : stats) {
            result.add(mapToUniqueHitStatistics(uriStatistics));
        }

        return result;
    }

    public static UriStatisticsDtoWithHits mapToNewUriStatisticsDtoWithHits(Statistics stat) {
        UriStatisticsDtoWithHits uriStatistics = new UriStatisticsDtoWithHits();
        uriStatistics.setId(stat.getId());
        uriStatistics.setApp(stat.getApp());
        uriStatistics.setUri(stat.getUri());
        uriStatistics.setIp(stat.getIp());
        uriStatistics.setTimestamp(stat.getTime());
        uriStatistics.setHits(stat.getHits());
        uriStatistics.setUnique_hits(stat.getUnique());
        return uriStatistics;
    }

    public static Statistics mapToNewUriStatistics(UriStatisticsDto stat, Long hits, Long uniqueHits) {
        Statistics uriStatistics = new Statistics();
        uriStatistics.setId(stat.getId());
        uriStatistics.setApp(stat.getApp());
        uriStatistics.setUri(stat.getUri());
        uriStatistics.setIp(stat.getIp());
        uriStatistics.setTime(stat.getTimestamp());
        uriStatistics.setHits(hits);
        uriStatistics.setUnique(uniqueHits);
        return uriStatistics;
    }
}