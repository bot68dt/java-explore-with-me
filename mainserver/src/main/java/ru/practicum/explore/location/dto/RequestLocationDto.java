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
    private List<String> town;
    private List<String> street;
    private List<String> place;
    private Map<Float, Float> latLon;
}