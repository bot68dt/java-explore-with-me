package ru.practicum.explore.client.client;

import java.util.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explore.location.dto.ImageDto;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        converter.setPrettyPrint(true);
        messageConverters.add(converter);
        rest.setMessageConverters(messageConverters);
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(boolean isImage, String path) {
        return get(isImage, path, null, null);
    }

    protected ResponseEntity<Object> get(boolean isImage, String path, long userId) {
        return get(isImage, path, userId, null);
    }

    protected ResponseEntity<Object> get(boolean isImage, String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(isImage, HttpMethod.GET, path, userId, parameters, null);
    }

    protected <T> ResponseEntity<Object> get(boolean isImage, String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(isImage, HttpMethod.GET, path, userId, parameters, body);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, long userId, T body) {
        return post(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(false, HttpMethod.POST, path, userId, parameters, body);
    }

    protected <T> ResponseEntity<Object> put(String path, long userId, T body) {
        return put(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> put(String path, long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(false, HttpMethod.PUT, path, userId, parameters, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, T body) {
        return patch(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId) {
        return patch(path, userId, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId, T body) {
        return patch(path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(false, HttpMethod.PATCH, path, userId, parameters, body);
    }

    protected ResponseEntity<Object> delete(String path) {
        return delete(path, null, null);
    }

    protected ResponseEntity<Object> delete(String path, long userId) {
        return delete(path, userId, null);
    }

    protected ResponseEntity<Object> delete(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(false, HttpMethod.DELETE, path, userId, parameters, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(boolean isImage, HttpMethod method, String path, Long userId, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(userId));
        if (isImage) {
            ResponseEntity<ImageDto> shareitServerResponse;
            try {
                if (parameters != null) {
                    shareitServerResponse = rest.exchange(path, method, requestEntity, ImageDto.class, parameters);
                } else {
                    shareitServerResponse = rest.exchange(path, method, requestEntity, ImageDto.class);
                }
            } catch (HttpStatusCodeException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
            }
            return prepareGatewayResponseImage(shareitServerResponse);
        } else {
            ResponseEntity<Object> shareitServerResponse;
            try {
                if (parameters != null) {
                    shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
                } else {
                    shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class);
                }
            } catch (HttpStatusCodeException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
            }
            return prepareGatewayResponse(shareitServerResponse);
        }
    }

    private HttpHeaders defaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }

    private static ResponseEntity<Object> prepareGatewayResponseImage(ResponseEntity<ImageDto> response) {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentLength(response.getBody().getImage().length);
        return responseBuilder.body(response.getBody().getImage());
    }
}