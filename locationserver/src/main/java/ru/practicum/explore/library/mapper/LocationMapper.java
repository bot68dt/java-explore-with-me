package ru.practicum.explore.library.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore.library.dto.*;
import ru.practicum.explore.library.model.AdLocation;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {

    public static AdLocationDto mapToAdLocationDto(AdLocation adLocation) {
        return new AdLocationDto(adLocation.getTown(), adLocation.getStreet(), adLocation.getTown(), adLocation.getLat(), adLocation.getLon());
    }

    public static List<AdLocationDto> mapToAdLocationDto(Iterable<AdLocation> adLocation) {
        List<AdLocationDto> result = new ArrayList<>();

        for (AdLocation adLocation1 : adLocation) {
            result.add(mapToAdLocationDto(adLocation1));
        }

        return result;
    }

    public static AdLocation mapToAdLocation(AdLocationDto adLocationDto) {
        AdLocation adLocation = new AdLocation();
        adLocation.setTown(adLocationDto.getTown());
        adLocation.setStreet(adLocationDto.getStreet());
        adLocation.setPlace(adLocationDto.getPlace());
        adLocation.setLat(adLocationDto.getLat());
        adLocation.setLon(adLocationDto.getLon());
        return adLocation;
    }

    public static List<AdLocation> mapToAdLocation(Iterable<AdLocationDto> adLocationDtos) {
        List<AdLocation> result = new ArrayList<>();

        for (AdLocationDto adLocation : adLocationDtos) {
            result.add(mapToAdLocation(adLocation));
        }

        return result;
    }
}