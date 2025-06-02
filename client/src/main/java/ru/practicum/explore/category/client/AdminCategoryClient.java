package ru.practicum.explore.category.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.client.client.BaseClient;

import java.util.Map;

@Service
public class AdminCategoryClient extends BaseClient {
    private static final String API_PREFIX = "/admin/categories";

    @Autowired
    public AdminCategoryClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> changeCategory(long catId, CategoryDto categoryDto) {
        Map<String, Object> parameters = Map.of("catId", catId);
        return patch("/{catId}", null, parameters, categoryDto);
    }

    public ResponseEntity<Object> deleteCategory(long catId) {
        Map<String, Object> parameters = Map.of("catId", catId);
        return delete("/{catId}", null, parameters);
    }

    public ResponseEntity<Object> createCategory(CategoryDto categoryDto) {
        return post("", null, null, categoryDto);
    }
}