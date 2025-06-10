package ru.practicum.explore.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdLocationDto {
    private String town;
    private String street;
    private String place;
    private Float lat;
    private Float lon;
}