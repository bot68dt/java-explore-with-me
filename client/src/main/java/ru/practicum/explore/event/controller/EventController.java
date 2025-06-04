package ru.practicum.explore.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.client.AdminEventClient;
import ru.practicum.explore.event.client.EventClient;
import ru.practicum.explore.event.dto.EventDto;
import ru.practicum.explore.event.dto.PatchEventDto;
import ru.practicum.explore.statistics.client.StatisticsClient;
import ru.practicum.explore.statistics.dto.StatisticsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventClient eventClient;
    private final AdminEventClient adminEventClient;
    private final StatisticsClient statisticsClient;

    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> getEventById(@Valid @PathVariable long userId, @Valid @PathVariable long eventId) {
        log.info("Request to get event with ID {} of user with ID {} received.", eventId, userId);
        return eventClient.getEventById(userId, eventId);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Object> getPublishedEventById(@Valid @PathVariable long id, HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        log.info("Request to get published event with ID {} received.", id);
        StatisticsDto statisticsDto = new StatisticsDto(null, "main-server-app", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        LinkedHashMap<String, Object> statisticsDtoWithHits = LinkedHashMap.class.cast(statisticsClient.addUri(statisticsDto).getBody());
        Integer hits = (Integer) statisticsDtoWithHits.get("unique");
        return eventClient.getPublishedEventById(id, hits);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Object> getEvents(@Valid @PathVariable long userId, @Valid @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @Valid @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get events of the user received.");
        return eventClient.getAllUserEvents(userId, from, size);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<Object> findEventsByAdmin(@Valid @RequestParam(name = "users", defaultValue = "0") Long users, @Valid @RequestParam(name = "states", defaultValue = "0") String states, @Valid @RequestParam(name = "categories", defaultValue = "0") Long categories, @Valid @RequestParam(name = "rangeStart", defaultValue = "2022-01-06 00:00:00") String rangeStart, @Valid @RequestParam(name = "rangeEnd", defaultValue = "2097-09-06 00:00:00") String rangeEnd, @Valid @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @Valid @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get events by Admin received.");
        LocalDateTime startDecoded = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime endDecoded = LocalDateTime.parse(rangeEnd, formatter);
        return adminEventClient.findEventsByAdmin(users, states, categories, startDecoded, endDecoded, from, size);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> findEventsByUser(@Valid @RequestParam(required = false, name = "text", defaultValue = " ") String text, @Valid @RequestParam(required = false, name = "categories", defaultValue = "0") Long categories, @Valid @RequestParam(required = false, name = "paid", defaultValue = "1") Boolean paid, @Valid @RequestParam(required = false, name = "rangeStart", defaultValue = "2022-01-06 00:00:00") String rangeStart, @Valid @RequestParam(required = false, name = "rangeEnd", defaultValue = "2097-09-06 00:00:00") String rangeEnd, @Valid @RequestParam(required = false, name = "onlyAvailable", defaultValue = "1") Boolean onlyAvailable, @Valid @RequestParam(required = false, name = "sort", defaultValue = "VIEWS") String sort, @Valid @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @Valid @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get events by User received {}.", text);
        LocalDateTime startDecoded = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime endDecoded = LocalDateTime.parse(rangeEnd, formatter);
        return eventClient.findEventsByUser(text, categories, paid, startDecoded, endDecoded, onlyAvailable, sort, from, size);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<Object> changeEvent(@Valid @PathVariable long userId, @Valid @PathVariable long eventId, @Valid @RequestBody PatchEventDto eventDto) {
        log.info("Request to change event {} of user with ID {} received.", eventId, userId);
        return eventClient.changeEvent(userId, eventId, eventDto);
    }

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<Object> changeEventByAdmin(@Valid @PathVariable long eventId, @Valid @RequestBody PatchEventDto eventDto) {
        log.info("Request to change event with ID {} received.", eventId);
        return adminEventClient.changeEventByAdmin(eventId, eventDto);
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Object> createEvent(@Valid @PathVariable long userId, @Valid @RequestBody EventDto newEventDto) {
        log.info("Request to create new event received: {}", newEventDto);
        return eventClient.createEvent(userId, newEventDto);
    }
}