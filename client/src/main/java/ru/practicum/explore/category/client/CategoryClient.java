package ru.practicum.explore.category.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;

import java.util.Map;

@Service
public class CategoryClient extends BaseClient {
    private static final String API_PREFIX = "/categories";

    @Autowired
    public CategoryClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getCategory(Long catId) {
        Map<String, Object> parameters = Map.of("catId", catId);
        return get("/{catId}", null, parameters);
    }

    public ResponseEntity<Object> getAllCategories(Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get("?from={from}&size={size}", null, parameters);
    }
}