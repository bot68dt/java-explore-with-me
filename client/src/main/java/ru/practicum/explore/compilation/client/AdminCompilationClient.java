package ru.practicum.explore.compilation.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.compilation.dto.CompilationDto;

import java.util.Map;

@Service
public class AdminCompilationClient extends BaseClient {
    private static final String API_PREFIX = "/admin/compilations";

    @Autowired
    public AdminCompilationClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> changeCompilation(long compId, CompilationDto compilationDto) {
        Map<String, Object> parameters = Map.of("compId", compId);
        return patch("/{compId}", null, parameters, compilationDto);
    }

    public ResponseEntity<Object> deleteCompilation(long compId) {
        Map<String, Object> parameters = Map.of("compId", compId);
        return delete("/{compId}", null, parameters);
    }

    public ResponseEntity<Object> createCompilation(CompilationDto compilationDto) {
        return post("", null, null, compilationDto);
    }
}