package ru.practicum.main.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.RequestCompilationDto;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.mapper.EventMapperNew;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapperNew {
    public static CompilationDto mapToCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setEvents(EventMapperNew.mapToResponseEventDto(compilation.getEvents()));
        return compilationDto;
    }

    public static List<CompilationDto> mapToCompilationDto(Iterable<Compilation> compilations) {
        List<CompilationDto> result = new ArrayList<>();

        for (Compilation compilation : compilations) {
            result.add(mapToCompilationDto(compilation));
        }

        return result;
    }

    public static Compilation changeCompilation(Compilation compilation, RequestCompilationDto requestCompilationDto) {
        compilation.setTitle(requestCompilationDto.getTitle());
        compilation.setPinned(requestCompilationDto.getPinned());
        return compilation;
    }
}