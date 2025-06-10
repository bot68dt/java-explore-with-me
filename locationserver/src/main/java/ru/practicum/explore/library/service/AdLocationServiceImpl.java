package ru.practicum.explore.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.explore.library.dto.*;
import ru.practicum.explore.library.mapper.LocationMapper;
import ru.practicum.explore.library.model.AdLocation;
import ru.practicum.explore.library.repository.AdLocationRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AdLocationServiceImpl implements AdLocationService {

    private final AdLocationRepository adLocationRepository;

    @Override
    @Validated
    public Collection<AdLocationDto> getLocations() {
        return LocationMapper.mapToAdLocationDto(adLocationRepository.findAll());
    }

    @Override
    @Transactional
    public AdLocation addAdLocation(AdLocationDto adLocationDto) {
        return adLocationRepository.saveAndFlush(LocationMapper.mapToAdLocation(adLocationDto));
    }
}