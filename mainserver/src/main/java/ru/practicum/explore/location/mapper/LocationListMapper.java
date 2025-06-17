package ru.practicum.explore.location.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import ru.practicum.explore.location.dto.LocationDto;
import ru.practicum.explore.location.model.Location;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface LocationListMapper {
    List<Location> toModelList(Collection<LocationDto> models);

    List<LocationDto> toDTOList(Page<Location> models);

    List<LocationDto> toDTOlList(Collection<Location> models);
}