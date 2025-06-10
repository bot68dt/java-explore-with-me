package ru.practicum.explore.library.service;

import ru.practicum.explore.library.dto.AdLocationDto;
import ru.practicum.explore.library.model.AdLocation;

import java.util.Collection;

public interface AdLocationService {
    Collection<AdLocationDto> getLocations();

    AdLocation addAdLocation(AdLocationDto adLocationDto);
}