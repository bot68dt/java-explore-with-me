package ru.practicum.main.compilation.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.RequestCompilationDto;
import ru.practicum.main.compilation.mapper.CompilationMapperNew;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.model.Compilationevents;
import ru.practicum.main.compilation.repository.CompilationRepository;
import ru.practicum.main.compilation.repository.CompilationeventsRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationeventsRepository compilationeventsRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto getCompilation(long compId) {
        Optional<Compilation> compilation = compilationRepository.findById(compId);
        if (compilation.isPresent()) return CompilationMapperNew.mapToCompilationDto(compilation.get());
        else throw new EntityNotFoundException();
    }

    @Override
    public Collection<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<Compilation> compilations = compilationRepository.findByPinned(pinned, page);
        return CompilationMapperNew.mapToCompilationDto(compilations);
    }

    @Override
    @Transactional
    public CompilationDto changeCompilation(long compId, RequestCompilationDto requestCompilationDto) {
        Optional<Compilation> compilation = compilationRepository.findById(compId);
        if (compilation.isPresent()) {
            if (requestCompilationDto.getEvents().size() != eventRepository.findAllById(requestCompilationDto.getEvents()).size())
                throw new EntityNotFoundException();
            Collection<Compilationevents> compilationevents = compilationeventsRepository.findByCompilationId(compId);
            compilationeventsRepository.deleteAll(compilationevents);
            compilationevents = new ArrayList<>();
            for (Long id : requestCompilationDto.getEvents()) {
                compilationevents.add(new Compilationevents(0L, compId, id));
            }
            compilationeventsRepository.saveAllAndFlush(compilationevents);
        }
        compilation = compilationRepository.findById(compId);
        return CompilationMapperNew.mapToCompilationDto(compilationRepository.saveAndFlush(CompilationMapperNew.changeCompilation(compilation.get(), requestCompilationDto)));
    }

    @Override
    @Transactional
    public void deleteCompilation(long compId) {
        compilationRepository.deleteById(compId);
        Collection<Compilationevents> compilationevents = compilationeventsRepository.findByCompilationId(compId);
        compilationeventsRepository.deleteAll(compilationevents);
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(RequestCompilationDto requestCompilationDto) {
        List<Event> events = eventRepository.findAllById(requestCompilationDto.getEvents());
        if (requestCompilationDto.getEvents().size() != events.size()) throw new EntityNotFoundException();
        Compilation compilation = new Compilation();
        compilation = CompilationMapperNew.changeCompilation(compilation, requestCompilationDto);
        compilation.setEvents(events);
        compilation = compilationRepository.saveAndFlush(compilation);
        Collection<Compilationevents> compilationevents = new ArrayList<>();
        for (Long id : requestCompilationDto.getEvents()) {
            compilationevents.add(new Compilationevents(0L, compilation.getId(), id));
        }
        compilationeventsRepository.saveAllAndFlush(compilationevents);
        return CompilationMapperNew.mapToCompilationDto(compilation);
    }
}