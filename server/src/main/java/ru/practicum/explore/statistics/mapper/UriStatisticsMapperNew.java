package ru.practicum.explore.statistics.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore.statistics.dto.*;
import ru.practicum.explore.statistics.model.Statistics;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriStatisticsMapperNew {

    public static HitStatisticsDto mapToHitStatistics(Object[] uriStatistics) {
        return new HitStatisticsDto((String) uriStatistics[1], (String) uriStatistics[0], (Long) uriStatistics[2]);
    }

    public static List<HitStatisticsDto> mapToHitStatistics(Iterable<Object[]> statistics) {
        List<HitStatisticsDto> result = new ArrayList<>();

        for (Object[] stat : statistics) {
            result.add(mapToHitStatistics(stat));
        }

        return result;
    }

    public static UriStatisticsDtoWithHits mapToNewUriStatisticsDtoWithHits(Statistics stat) {
        UriStatisticsDtoWithHits uriStatistics = new UriStatisticsDtoWithHits();
        uriStatistics.setId(stat.getId());
        uriStatistics.setApp(stat.getApp());
        uriStatistics.setUri(stat.getUri());
        uriStatistics.setIp(stat.getIp());
        uriStatistics.setTimestamp(stat.getTimestamp());
        uriStatistics.setHits(stat.getHits());
        uriStatistics.setUnique(stat.getUnique());
        return uriStatistics;
    }

    public static Statistics mapToNewUriStatistics(UriStatisticsDto stat, Long hits, Long uniqueHits) {
        Statistics uriStatistics = new Statistics();
        uriStatistics.setId(stat.getId());
        uriStatistics.setApp(stat.getApp());
        uriStatistics.setUri(stat.getUri());
        uriStatistics.setIp(stat.getIp());
        uriStatistics.setTimestamp(stat.getTimestamp());
        uriStatistics.setHits(hits);
        uriStatistics.setUnique(uniqueHits);
        return uriStatistics;
    }
}