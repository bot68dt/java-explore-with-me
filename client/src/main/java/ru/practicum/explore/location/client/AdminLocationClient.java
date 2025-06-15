package ru.practicum.explore.location.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.RequestLocationDto;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class AdminLocationClient extends BaseClient {
    private static final String API_PREFIX = "/admin";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public AdminLocationClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getAllLocations(boolean isImage, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get(isImage, "/locations?from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> createLocation(LocationDto locationDto) {
        return post("/location", null, null, locationDto);
    }

    public ResponseEntity<Object> deleteLocation(long id) {
        Map<String, Object> parameters = Map.of("id", id);
        return delete("/location/{id}", null, parameters);
    }

    public ResponseEntity<Object> changeLocation(long id, LocationDto locationDto) {
        Map<String, Object> parameters = Map.of("id", id);
        return patch("/location/{id}", null, parameters, locationDto);
    }

    public ResponseEntity<Object> findLocations(RequestLocationDto requestLocationDto, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get(false, "/location?from={from}&size={size}", null, parameters, requestLocationDto);
    }

    public ResponseEntity<Object> getLocationById(long id) {
        Map<String, Object> parameters = Map.of("id", id);
        return get(false, "/location/{id}", null, parameters);
    }

    public ResponseEntity<Object> getLocationsOnMap(Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get(true, "/locations/map/all?from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> getLocationByIdOnMap(long id) {
        Map<String, Object> parameters = Map.of("id", id);
        return get(true, "/locations/map/{id}", null, parameters);
    }

    public ResponseEntity<Object> findSelectedLocationsOnMap(RequestLocationDto requestLocationDto, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get(true, "/locations/map/selected?from={from}&size={size}", null, parameters, requestLocationDto);
    }

    public ResponseEntity<Object> findLocationsInRadius(float lat, float lon, float radius, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("lat", lat, "lon", lon, "radius", radius, "from", from, "size", size);
        return get(false, "/locations/radius?lat={lat}&lon={lon}&radius={radius}&from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> findLocationsInRadiusOnMap(float lat, float lon, float radius, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("lat", lat, "lon", lon, "radius", radius, "from", from, "size", size);
        return get(true, "/locations/map/radius?lat={lat}&lon={lon}&radius={radius}&from={from}&size={size}", null, parameters);
    }
}