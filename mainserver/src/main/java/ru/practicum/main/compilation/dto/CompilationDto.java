package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.event.dto.ResponseEventDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<ResponseEventDto> events;
}