package ru.practicum.explore.location.service;

import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationWithRadiusDto;
import ru.practicum.explore.location.dto.RequestLocationDto;
import ru.practicum.explore.location.model.Location;

import java.util.Collection;

public interface LocationService {
    Collection<LocationDto> getLocations(Integer from, Integer size);

    Location addAdLocation(LocationDto adLocationDto);

    void deleteLocation(long id);

    LocationDto changeAdLocation(long id, LocationDto adLocationDto);

    Collection<LocationDto> findAdLocations(RequestLocationDto requestAdLocationDto, Integer from, Integer size);

    LocationDto getLocationById(Long id);

    Collection<LocationWithRadiusDto> findLocationsInRadius(float lat, float lon, float radius, Integer from, Integer size);
}