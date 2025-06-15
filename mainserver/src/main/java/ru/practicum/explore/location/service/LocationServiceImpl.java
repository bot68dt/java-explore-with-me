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
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.location.repository.LocationRepository;
import ru.practicum.explore.location.mapper.LocationMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {

    private final LocationRepository adLocationRepository;

    @Override
    public Collection<LocationDto> getLocations(Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return LocationMapper.mapToAdLocationDto(adLocationRepository.findAll(page));
    }

    @Override
    @Transactional
    public Location addAdLocation(LocationDto adLocationDto) {
        return adLocationRepository.saveAndFlush(LocationMapper.mapToAdLocation(adLocationDto));
    }

    @Override
    @Transactional
    public void deleteLocation(long id) {
        Optional<Location> location = adLocationRepository.findById(id);
        if (location.isPresent()) adLocationRepository.deleteById(id);
        else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public LocationDto changeAdLocation(long id, LocationDto adLocationDto) {
        Optional<Location> adLocation = adLocationRepository.findById(id);
        if (adLocation.isPresent()) {
            Location location = LocationMapper.changeAdLocation(adLocation.get(), adLocationDto);
            return LocationMapper.mapToAdLocationDto(adLocationRepository.saveAndFlush(location));
        } else throw new EntityNotFoundException();
    }

    @Override
    public Collection<LocationDto> findAdLocations(RequestLocationDto requestAdLocationDto, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<LocationDto> result = new ArrayList<>();
        if (!requestAdLocationDto.getTown().equals(List.of("null"))) {
            if (!requestAdLocationDto.getStreet().equals(List.of("null"))) {
                if (!requestAdLocationDto.getPlace().equals(List.of("null"))) {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndPlaceIgnoreCaseInAndStreetIgnoreCaseInAndTownIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getPlace(), requestAdLocationDto.getStreet(), requestAdLocationDto.getTown(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByPlaceIgnoreCaseInAndStreetIgnoreCaseInAndTownIgnoreCaseIn(requestAdLocationDto.getPlace(), requestAdLocationDto.getStreet(), requestAdLocationDto.getTown(), page)));
                    }
                } else {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndStreetIgnoreCaseInAndTownIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getStreet(), requestAdLocationDto.getTown(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByStreetIgnoreCaseInAndTownIgnoreCaseIn(requestAdLocationDto.getStreet(), requestAdLocationDto.getTown(), page)));
                    }
                }
            } else {
                if (!requestAdLocationDto.getPlace().equals(List.of("null"))) {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndPlaceIgnoreCaseInAndTownIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getPlace(), requestAdLocationDto.getTown(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByPlaceIgnoreCaseInAndTownIgnoreCaseIn(requestAdLocationDto.getPlace(), requestAdLocationDto.getTown(), page)));
                    }
                } else {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndTownIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getTown(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByTownIgnoreCaseIn(requestAdLocationDto.getTown(), page)));
                    }
                }
            }
        } else {
            if (!requestAdLocationDto.getStreet().equals(List.of("null"))) {
                if (!requestAdLocationDto.getPlace().equals(List.of("null"))) {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndPlaceIgnoreCaseInAndStreetIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getPlace(), requestAdLocationDto.getStreet(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByPlaceIgnoreCaseInAndStreetIgnoreCaseIn(requestAdLocationDto.getPlace(), requestAdLocationDto.getStreet(), page)));
                    }
                } else {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndStreetIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getStreet(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByStreetIgnoreCaseIn(requestAdLocationDto.getStreet(), page)));
                    }
                }
            } else {
                if (!requestAdLocationDto.getPlace().equals(List.of("null"))) {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonInAndPlaceIgnoreCaseIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), requestAdLocationDto.getPlace(), page)));
                    } else {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByPlaceIgnoreCaseIn(requestAdLocationDto.getPlace(), page)));
                    }
                } else {
                    if (!requestAdLocationDto.getLatLon().equals(Map.of(0f, 0f))) {
                        result.addAll(LocationMapper.mapToAdLocationDto(adLocationRepository.findByLatInAndLonIn(requestAdLocationDto.getLatLon().keySet(), requestAdLocationDto.getLatLon().values(), page)));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public LocationDto getLocationById(Long id) {
        Optional<Location> adLocation = adLocationRepository.findById(id);
        if (adLocation.isPresent()) return LocationMapper.mapToAdLocationDto(adLocation.get());
        else throw new EntityNotFoundException();
    }

    @Override
    public Collection<LocationWithRadiusDto> findLocationsInRadius(float lat, float lon, float radius, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<Object[]> result = adLocationRepository.findAllInRadius(lat, lon, radius, page);
        return LocationMapper.mapToAdLocationWithRadiusDto(result);
    }
}