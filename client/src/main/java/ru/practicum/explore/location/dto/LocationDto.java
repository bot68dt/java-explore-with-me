package ru.practicum.explore.location.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @NotBlank
    @Length(min = 2)
    private String town = "null";
    @NotBlank
    @Length(min = 2)
    private String street = "null";
    @NotBlank
    @Length(min = 2)
    private String place = "null";
    private Float lat = 0f;
    private Float lon = 0f;
}