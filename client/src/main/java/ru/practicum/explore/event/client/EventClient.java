package ru.practicum.explore.event.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.event.dto.EventDto;
import ru.practicum.explore.event.dto.PatchEventDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EventClient extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public EventClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getEventById(Long userId, Long eventId) {
        Map<String, Object> parameters = Map.of("userId", userId, "eventId", eventId);
        return get("users/{userId}/events/{eventId}", null, parameters);
    }

    public ResponseEntity<Object> getPublishedEventById(Long id, Integer views) {
        Map<String, Object> parameters = Map.of("id", id, "views", views);
        return get("events/{id}?views={views}", null, parameters);
    }

    public ResponseEntity<Object> getAllUserEvents(Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("userId", userId, "from", from, "size", size);
        return get("users/{userId}/events", null, parameters);
    }

    public ResponseEntity<Object> findEventsByUser(String text, Long categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("text", text, "categories", categories, "paid", paid, "rangeStart", rangeStart, "rangeEnd", rangeEnd, "onlyAvailable", onlyAvailable,  "sort", sort, "from", from, "size", size);
        return get("/events?text={text}&categories={categories}&paid={paid}&rangeStart={rangeStart}&rangeEnd={rangeEnd}&onlyAvailable={onlyAvailable}&sort={sort}&from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> changeEvent(Long userId, Long eventId, PatchEventDto eventDto) {
        Map<String, Object> parameters = Map.of("userId", userId, "eventId", eventId);
        return patch("users/{userId}/events/{eventId}", null, parameters, eventDto);
    }

    public ResponseEntity<Object> createEvent(long userId, EventDto newEventDto) {
        Map<String, Object> parameters = Map.of("userId", userId);
        return post("users/{userId}/events", null, parameters, newEventDto);
    }
}