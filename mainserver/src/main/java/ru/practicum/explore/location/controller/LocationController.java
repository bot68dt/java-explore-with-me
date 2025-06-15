package ru.practicum.explore.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.practicum.explore.location.dto.ImageDto;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationWithRadiusDto;
import ru.practicum.explore.location.dto.RequestLocationDto;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.service.LocationService;
import ru.practicum.explore.location.mapper.LocationMapper;

import java.io.IOException;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class LocationController {
    private final LocationService adLocationService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/admin/locations")
    public ResponseEntity<Collection<LocationDto>> getAllLocations(@RequestParam Integer from, @RequestParam Integer size) {
        log.info("Request to get all locations received.");
        return ResponseEntity.ok().body(adLocationService.getLocations(from, size));
    }

    @PostMapping("/admin/location")
    public ResponseEntity<LocationDto> createAdLocation(@RequestBody LocationDto adLocationDto) {
        log.info("Request to create new location received:");
        Location adLocation = adLocationService.addAdLocation(adLocationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adLocation.getId()).toUri();
        log.info("New uriStatistics created with ID {}", adLocation.getId());
        return ResponseEntity.created(location).body(LocationMapper.mapToAdLocationDto(adLocation));
    }

    @DeleteMapping("/admin/location/{id}")
    public ResponseEntity<Void> deleteAdLocation(@PathVariable long id) {
        log.info("Request to delete location with ID {} received.", id);
        adLocationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/admin/location/{id}")
    public ResponseEntity<LocationDto> changeAdLocation(@PathVariable long id, @RequestBody LocationDto adLocationDto) {
        log.info("Request to change location {} received.", adLocationDto);
        return ResponseEntity.ok().body(adLocationService.changeAdLocation(id, adLocationDto));
    }

    @GetMapping("/admin/location")
    public ResponseEntity<Collection<LocationDto>> findLocations(@RequestBody RequestLocationDto requestAdLocationDto, @RequestParam Integer from, @RequestParam Integer size) {
        log.info("Request to get locations received.");
        return ResponseEntity.ok().body(adLocationService.findAdLocations(requestAdLocationDto, from, size));
    }

    @GetMapping("/admin/location/{id}")
    public ResponseEntity<LocationDto> findLocationById(@PathVariable long id) {
        log.info("Request to get location by id:{} received.", id);
        return ResponseEntity.ok().body(adLocationService.getLocationById(id));
    }

    @GetMapping("/admin/locations/map/all")
    public ResponseEntity<ImageDto> getLocationsOnMap(@RequestParam Integer from, @RequestParam Integer size) throws IOException {
        log.info("Request to get locations received.");
        return ResponseEntity.ok().body(new ImageDto(LocationMapper.getMap(LocationMapper.mapToAdLocation(adLocationService.getLocations(from, size)))));
    }

    @GetMapping("/admin/locations/map/{id}")
    public ResponseEntity<ImageDto> getLocationOnMap(@PathVariable long id) throws IOException {
        log.info("Request to get location by id:{} received.", id);
        return ResponseEntity.ok().body(new ImageDto(LocationMapper.getMap(List.of(LocationMapper.mapToAdLocation(adLocationService.getLocationById(id))))));
    }

    @GetMapping("/admin/locations/map/selected")
    public ResponseEntity<Object> getSelectedLocationsOnMap(@RequestBody RequestLocationDto requestAdLocationDto, @RequestParam Integer from, @RequestParam Integer size) throws IOException {
        log.info("Request to get selected locations received.");
        return ResponseEntity.ok().body(new ImageDto(LocationMapper.getMap(LocationMapper.mapToAdLocation(adLocationService.findAdLocations(requestAdLocationDto, from, size)))));
    }

    @GetMapping("/admin/locations/radius")
    public ResponseEntity<Collection<LocationWithRadiusDto>> findLocationsInRadius(@RequestParam float lat, @RequestParam float lon, @RequestParam float radius, @RequestParam Integer from, @RequestParam Integer size) {
        log.info("Request to get locations in radius {} received.", radius);
        return ResponseEntity.ok().body(adLocationService.findLocationsInRadius(lat, lon, radius, from, size));
    }

    @GetMapping("/admin/locations/map/radius")
    public ResponseEntity<Object> findLocationsInRadiusOnMap(@RequestParam float lat, @RequestParam float lon, @RequestParam float radius, @RequestParam Integer from, @RequestParam Integer size) throws IOException {
        log.info("Request to get locations in radius {} on map received.", radius);
        return ResponseEntity.ok().body(new ImageDto(LocationMapper.getMapWithRadius(lat, lon, adLocationService.findLocationsInRadius(lat, lon, radius, from, size))));
    }
}