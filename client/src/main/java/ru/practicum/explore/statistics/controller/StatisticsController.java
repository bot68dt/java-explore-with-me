package ru.practicum.explore.statistics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.statistics.client.StatisticsClient;
import ru.practicum.explore.statistics.dto.StatisticsDto;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatisticsController {

    private final StatisticsClient statisticsClient;

    @GetMapping("/stats")
    public ResponseEntity<Object> getUriStatisitcs(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end, @RequestParam(required = false, name = "uris", defaultValue = "") String uris, @RequestParam(required = false, name = "unique", defaultValue = "false") boolean unique) {
        log.info("Request to get uri {} received.", uris);
        return statisticsClient.getUris(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> createUriStatistics(@RequestBody @Valid StatisticsDto statisticsDto) {
        log.info("Request to create new uri statistics received: {}", statisticsDto);
        return statisticsClient.addUri(statisticsDto);
    }
}