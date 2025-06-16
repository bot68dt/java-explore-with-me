package ru.practicum.explore.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
import java.util.List;

@RestController
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
        Integer views = (Integer) LinkedHashMap.class.cast(statisticsClient.addUri(new StatisticsDto(null, "main-server-app", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))).getBody()).get("unique");
        return eventClient.getPublishedEventById(id, views);
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Object> getEvents(@Valid @PathVariable long userId, @Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get events of the user received.");
        return eventClient.getAllUserEvents(userId, from, size);
    }

    @GetMapping("/admin/events")
    public ResponseEntity<Object> findEventsByAdmin(@Valid @RequestParam(value = "users", defaultValue = "0") Long users, @Valid @RequestParam(value = "states", defaultValue = "") String states, @Valid @RequestParam(value = "categories", defaultValue = "0") Long categories, @Valid @RequestParam(defaultValue = "2022-01-06 00:00:00") String rangeStart, @Valid @RequestParam(defaultValue = "2097-09-06 00:00:00") String rangeEnd, @Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get events by Admin received.");
        return adminEventClient.findEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> findEventsByUser(@Valid @RequestParam(value = "text", defaultValue = "") String text, @Valid @RequestParam(value = "categories", defaultValue = "0") Long categories, @Valid @RequestParam(value = "paid", defaultValue = "0") Boolean paid, @Valid @RequestParam(defaultValue = " ") String rangeStart, @Valid @RequestParam(defaultValue = "2097-09-06 00:00:00") String rangeEnd, @Valid @RequestParam(defaultValue = "0") Boolean onlyAvailable, @Valid @RequestParam(defaultValue = "EVENT_DATE") String sort, @Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size, HttpServletRequest request) {
        log.info("Request to get events by User received {}.", text);
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        statisticsClient.addUri(new StatisticsDto(null, "main-server-app", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))).getBody();
        return eventClient.findEventsByUser(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
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

    @PatchMapping("/admin/events/{eventId}/location/{locationId}")
    public ResponseEntity<Object> changeLocationOfEventByAdminById(@Valid @PathVariable long eventId, @Valid @PathVariable long locationId) {
        log.info("Request to change location of the event with ID {} received.", eventId);
        return adminEventClient.changeLocationOfEventByAdminById(eventId, locationId);
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<Object> createEvent(@Valid @PathVariable long userId, @Valid @RequestBody EventDto newEventDto) {
        log.info("Request to create new event received: {}", newEventDto);
        return eventClient.createEvent(userId, newEventDto);
    }
}