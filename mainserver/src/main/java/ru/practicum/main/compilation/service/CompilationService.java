package ru.practicum.main.compilation.service;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.RequestCompilationDto;

import java.util.Collection;

public interface CompilationService {
    CompilationDto getCompilation(long compId);

    Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto changeCompilation(long compId, RequestCompilationDto requestCompilationDto);

    void deleteCompilation(long compId);

    CompilationDto createCompilation(RequestCompilationDto requestCompilationDto);
}