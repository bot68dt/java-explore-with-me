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
    private List<String> town = List.of("null");
    private List<String> street = List.of("null");
    private List<String> place = List.of("null");
    private Map<Float, Float> latLon = Map.of(0f, 0f);
}