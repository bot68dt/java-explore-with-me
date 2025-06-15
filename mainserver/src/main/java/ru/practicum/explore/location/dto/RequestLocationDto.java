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
    private List<String> Town;
    private List<String> Street;
    private List<String> Place;
    private Map<Float, Float> LatLon;
}