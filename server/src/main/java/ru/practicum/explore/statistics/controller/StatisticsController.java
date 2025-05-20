package ru.practicum.explore.statistics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;
import ru.practicum.explore.statistics.model.Statistics;
import ru.practicum.explore.statistics.service.UriStatisticsService;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class StatisticsController {

    private final UriStatisticsService uriStatisticsService;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/stats")
    public ResponseEntity<List<HitStatisticsDto>> getUriStatistics(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end, @RequestParam(required = false, name = "uris", defaultValue = "") List<String> uris, @RequestParam(required = false, name = "unique", defaultValue = "false") boolean unique) {
        log.info("Request to get statistics of uris {} received.", uris);
        LocalDateTime start_l = LocalDateTime.parse(start,formatter);
        LocalDateTime end_l = LocalDateTime.parse(end,formatter);
        return ResponseEntity.ok(uriStatisticsService.getUriStatistics(start_l, end_l, uris, unique));
    }

    @PostMapping("/hit")
    public ResponseEntity<UriStatisticsDtoWithHits> createUriStatistics(@RequestBody UriStatisticsDto uriStatistics) {
        log.info("Request to create new uriStatistics received: {}", uriStatistics);
        UriStatisticsDtoWithHits uriStatisticsDto = uriStatisticsService.addUriStatistics(uriStatistics);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(uriStatisticsDto.getId()).toUri();
        log.info("New uriStatistics created with ID {}", uriStatisticsDto.getId());
        return ResponseEntity.created(location).body(uriStatisticsDto);
    }
}