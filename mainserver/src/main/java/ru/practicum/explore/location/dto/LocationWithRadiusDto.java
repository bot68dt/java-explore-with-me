package ru.practicum.explore.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationWithRadiusDto {
    private String town;
    private String street;
    private String place;
    private Float lat;
    private Float lon;
    private Double radius;
}