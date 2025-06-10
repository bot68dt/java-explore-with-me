package ru.practicum.explore.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.user.dto.RequestsDto;

import java.util.Map;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getUserRequests(Long userId) {
        Map<String, Object> parameters = Map.of("userId", userId);
        return get("/{userId}/requests", null, parameters);
    }

    public ResponseEntity<Object> getEventRequests(Long userId, Long eventId) {
        Map<String, Object> parameters = Map.of("userId", userId, "eventId", eventId);
        return get("/{userId}/events/{eventId}/requests", null, parameters);
    }

    public ResponseEntity<Object> cancelRequest(Long userId, Long requestId) {
        Map<String, Object> parameters = Map.of("userId", userId, "requestId", requestId);
        return patch("/{userId}/requests/{requestId}/cancel", null, parameters, null);
    }

    public ResponseEntity<Object> changeRequestsStatuses(Long userId, Long eventId, RequestsDto requestsDto) {
        Map<String, Object> parameters = Map.of("userId", userId, "eventId", eventId);
        return patch("/{userId}/events/{eventId}/requests", null, parameters, requestsDto);
    }

    public ResponseEntity<Object> createRequest(Long userId, Long eventId) {
        Map<String, Object> parameters = Map.of("userId", userId, "eventId", eventId);
        return post("/{userId}/requests?eventId={eventId}", null, parameters, null);
    }
}