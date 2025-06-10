package ru.practicum.explore.event.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.event.dto.PatchEventDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class AdminEventClient extends BaseClient {
    private static final String API_PREFIX = "/admin";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public AdminEventClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> findEventsByAdmin(long users, String states, long categories, String start, String end, Integer from, Integer size) {
        LocalDateTime rangeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime rangeEnd = LocalDateTime.parse(end, formatter);
        Map<String, Object> parameters = Map.of("users", users, "states", states, "categories", categories, "rangeStart", rangeStart, "rangeEnd", rangeEnd, "from", from, "size", size);
        return get("/events?users={users}&states={states}&categories={categories}&rangeStart={rangeStart}&rangeEnd={rangeEnd}&from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> changeEventByAdmin(long eventId, PatchEventDto eventDto) {
        Map<String, Object> parameters = Map.of("eventId", eventId);
        return patch("/events/{eventId}", null, parameters, eventDto);
    }
}