package ru.practicum.explore.location.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.dto.LocationWithRadiusDto;
import ru.practicum.explore.location.dto.RequestLocationDto;
import ru.practicum.explore.location.mapper.LocationListMapper;
import ru.practicum.explore.location.mapper.LocationMapper;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.repository.LocationRepository;
import ru.practicum.explore.location.mapper.SpecialLocationMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {

    private final LocationRepository adLocationRepository;
    private final LocationListMapper locationListMapper;
    private final LocationMapper locationMapper;

    @Override
    public Collection<LocationDto> getLocations(Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return locationListMapper.toDTOList(adLocationRepository.findAll(page));
    }

    @Override
    @Transactional
    public Location addAdLocation(LocationDto adLocationDto) {
        return adLocationRepository.saveAndFlush(locationMapper.toModel(adLocationDto));
    }

    @Override
    @Transactional
    public void deleteLocation(long id) {
        Optional<Location> location = adLocationRepository.findById(id);
        if (location.isPresent()) {
            adLocationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public LocationDto changeAdLocation(long id, LocationDto adLocationDto) {
        Optional<Location> adLocation = adLocationRepository.findById(id);
        if (adLocation.isPresent()) {
            Location location = SpecialLocationMapper.changeAdLocation(adLocation.get(), adLocationDto);
            return locationMapper.toDTO(adLocationRepository.saveAndFlush(location));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<LocationDto> findAdLocations(RequestLocationDto requestAdLocationDto, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        if (requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
            return locationListMapper.toDTOlList(adLocationRepository.findLocationByParams(null, null, requestAdLocationDto.getPlace(), requestAdLocationDto.getStreet(), requestAdLocationDto.getTown(), page));
        } else {
            return locationListMapper.toDTOlList(adLocationRepository.findLocationByParams(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getPlace(), requestAdLocationDto.getStreet(), requestAdLocationDto.getTown(), page));
        }
    }

    @Override
    public LocationDto getLocationById(Long id) {
        Optional<Location> adLocation = adLocationRepository.findById(id);
        if (adLocation.isPresent()) {
            return locationMapper.toDTO(adLocation.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<LocationWithRadiusDto> findLocationsInRadius(float lat, float lon, float radius, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<Object[]> result = adLocationRepository.findAllInRadius(lat, lon, radius, page);
        return SpecialLocationMapper.mapToAdLocationWithRadiusDto(result);
    }
}