package ru.practicum.explore.statistics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;
import ru.practicum.explore.statistics.service.UriStatisticsService;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
@Validated
public class StatisticsController {

    private final UriStatisticsService uriStatisticsService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/stats")
    public ResponseEntity<Collection<HitStatisticsDto>> getUriStatistics(@RequestParam(name = "start") byte[] start, @RequestParam(name = "end") byte[] end, @RequestParam(required = false, name = "uris", defaultValue = "") List<String> uris, @RequestParam(required = false, name = "unique", defaultValue = "false") boolean unique) {
        List<String> urisDecoded = new ArrayList<>();
        for (String uri : uris)
            urisDecoded.add(UriUtils.decode(uri, "UTF-8"));
        log.info("Request to get statistics of uris {} received.", uris);
        LocalDateTime startDecoded = LocalDateTime.parse(StringUtils.newStringUtf8(start), formatter);
        LocalDateTime endDecoded = LocalDateTime.parse(StringUtils.newStringUtf8(end), formatter);
        return ResponseEntity.ok().body(uriStatisticsService.getDecodedUriStatistics(startDecoded, endDecoded, urisDecoded, unique));
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