package ru.practicum.explore.compilation.client;

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
public class CompilationClient extends BaseClient {
    private static final String API_PREFIX = "/compilations";

    @Autowired
    public CompilationClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getCompilation(Long compId) {
        Map<String, Object> parameters = Map.of("compId", compId);
        return get("/{compId}", null, parameters);
    }

    public ResponseEntity<Object> getCompilations(Boolean pinned, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("pinned", pinned, "from", from, "size", size);
        return get("?pinned={pinned}&from={from}&size={size}", null, parameters);
    }
}