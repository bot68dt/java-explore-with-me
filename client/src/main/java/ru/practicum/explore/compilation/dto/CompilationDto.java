package ru.practicum.explore.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
    private Boolean pinned = false;
    private List<Long> events = List.of();
}