package ru.practicum.explore.location.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.location.client.AdminLocationClient;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.RequestLocationDto;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
@Validated
public class LocationController {
    private final AdminLocationClient locationClient;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/admin/locations")
    public ResponseEntity<Object> getAllLocations(@Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get all locations received.");
        return locationClient.getAllLocations(false, from, size);
    }

    @PostMapping("/admin/location")
    public ResponseEntity<Object> createLocation(@Valid @RequestBody LocationDto locationDto) {
        log.info("Request to create new location received:");
        return locationClient.createLocation(locationDto);
    }

    @DeleteMapping("/admin/location/{id}")
    public ResponseEntity<Object> deleteLocation(@Valid @PathVariable long id) {
        log.info("Request to delete location with ID {} received.", id);
        return locationClient.deleteLocation(id);
    }

    @PatchMapping("/admin/location/{id}")
    public ResponseEntity<Object> changeLocation(@Valid @PathVariable long id, @Valid @RequestBody LocationDto locationDto) {
        log.info("Request to change location {} received.", locationDto);
        return locationClient.changeLocation(id, locationDto);
    }

    @GetMapping("/admin/location")
    public ResponseEntity<Object> findLocations(@Valid @RequestBody RequestLocationDto requestAdLocationDto, @Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to find locations received.");
        return locationClient.findLocations(requestAdLocationDto, from, size);
    }

    @GetMapping("/admin/location/{id}")
    public ResponseEntity<Object> findLocationById(@Valid @PathVariable long id) {
        log.info("Request to get location by id:{} received.", id);
        return locationClient.getLocationById(id);
    }

    @GetMapping("/admin/locations/map/all")
    public ResponseEntity<Object> getLocationsOnMap(@Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get locations on map received.");
        return locationClient.getLocationsOnMap(from, size);
    }

    @GetMapping("/admin/locations/map/{id}")
    public ResponseEntity<Object> getLocationOnMap(@Valid @PathVariable long id) {
        log.info("Request to get location by id:{} received.", id);
        return locationClient.getLocationByIdOnMap(id);
    }

    @GetMapping("/admin/locations/map/selected")
    public ResponseEntity<Object> getSelectedLocationsOnMap(@Valid @RequestBody RequestLocationDto requestAdLocationDto, @Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get locations received.");
        return locationClient.findSelectedLocationsOnMap(requestAdLocationDto, from, size);
    }

    @GetMapping("/admin/locations/radius")
    public ResponseEntity<Object> findLocationsInRadius(@Valid @RequestParam float lat, @Valid @RequestParam float lon, @Valid @RequestParam float radius, @Valid @RequestParam(defaultValue = "0") Integer from, @Valid @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request to get locations in radius {} received.", radius);
        return locationClient.findLocationsInRadius(lat, lon, radius, from, size);
    }

    @GetMapping("/admin/locations/map/radius")
    public ResponseEntity<Object> findLocationsInRadiusOnMap(@RequestParam float lat, @RequestParam float lon, @RequestParam float radius, @RequestParam Integer from, @RequestParam Integer size) {
        log.info("Request to get locations in radius {} received.", radius);
        return locationClient.findLocationsInRadiusOnMap(lat, lon, radius, from, size);
    }
}