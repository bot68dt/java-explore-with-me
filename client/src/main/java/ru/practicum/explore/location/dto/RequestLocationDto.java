package ru.practicum.explore.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLocationDto {
    private List<String> Town = List.of("null");
    private List<String> Street = List.of("null");
    private List<String> Place = List.of("null");
    private Map<Float, Float> LatLon = Map.of(0f, 0f);
}