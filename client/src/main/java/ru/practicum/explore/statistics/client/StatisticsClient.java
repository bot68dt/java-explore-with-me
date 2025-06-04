package ru.practicum.explore.statistics.client;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriUtils;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.statistics.dto.StatisticsDto;

import java.util.Map;

@Service
public class StatisticsClient extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public StatisticsClient(@Value("http://localhost:9090") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getUris(String start, String end, String uris, boolean unique) {
        Map<String, Object> parameters = Map.of("start", StringUtils.getBytesUtf8(start), "end", StringUtils.getBytesUtf8(end), "uris", UriUtils.encodePath(uris, "UTF-8"), "unique", unique);
        return get("stats?start={start}&end={end}&uris={uris}&unique={unique}", null, parameters);
    }

    public ResponseEntity<Object> addUri(StatisticsDto requestDto) {
        return post("hit", requestDto);
    }
}