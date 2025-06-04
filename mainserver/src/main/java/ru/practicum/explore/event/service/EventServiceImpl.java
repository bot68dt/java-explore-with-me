package ru.practicum.explore.event.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.category.repository.CategoryRepository;
import ru.practicum.explore.event.dto.EventDto;
import ru.practicum.explore.event.dto.PatchEventDto;
import ru.practicum.explore.event.dto.ResponseEventDto;
import ru.practicum.explore.event.mapper.EventMapperNew;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.Location;
import ru.practicum.explore.user.model.User;
import ru.practicum.explore.event.repository.EventRepository;
import ru.practicum.explore.event.repository.LocationRepository;
import ru.practicum.explore.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

import static javax.print.attribute.standard.JobState.CANCELED;
import static javax.print.attribute.standard.JobState.PENDING;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Override
    public EventDto getEventById(long userId, long eventId) {
        Optional<Event> event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        if (event.isPresent()) return EventMapperNew.mapToEventDto(event.get());
        else throw new EntityNotFoundException();
    }

    @Override
    public Collection<ResponseEventDto> getAllUserEvents(long userId, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return EventMapperNew.mapToResponseEventDto(eventRepository.findByInitiatorId(userId, page));
    }

    @Override
    @Transactional
    public EventDto changeEvent(long userId, long eventId, PatchEventDto patchEventDto) {
        Optional<Event> event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        if (event.isPresent()) {
            if (event.get().getState().equals("PUBLISHED"))
                throw new DataIntegrityViolationException("Data integrity violation exception");
            Optional<Category> category;
            Category cat = null;
            if (patchEventDto.getCategory() != null) {
                category = categoryRepository.findById(patchEventDto.getCategory());
                if (category.isPresent()) cat = category.get();
            }
            Location location = null;
            if (patchEventDto.getLocation() != null)
                location = locationRepository.saveAndFlush(EventMapperNew.mapToLocation(patchEventDto.getLocation()));
            String state = event.get().getState();
            Event newEvent = EventMapperNew.changeEvent(event.get(), patchEventDto);
            if (cat != null) newEvent.setCategory(cat);
            if (location != null) newEvent.setLocation(location);
            if (patchEventDto.getStateAction() != null) {
                switch (patchEventDto.getStateAction()) {
                    case "SEND_TO_REVIEW":
                        newEvent.setState(PENDING.toString().toUpperCase());
                        break;
                    default:
                }
            } else newEvent.setState(state);
            return EventMapperNew.mapToEventDto(eventRepository.saveAndFlush(newEvent));
        } else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public EventDto createEvent(long userId, PatchEventDto newEventDto) {
        Optional<Category> category = categoryRepository.findById(newEventDto.getCategory());
        Optional<User> user = userRepository.findById(userId);
        if (category.isPresent() && user.isPresent()) {
            Event event = new Event();
            event = EventMapperNew.changeEvent(event, newEventDto);
            Location location = locationRepository.saveAndFlush(EventMapperNew.mapToLocation(newEventDto.getLocation()));
            event.setLocation(location);
            event.setCategory(category.get());
            event.setInitiator(user.get());
            event.setViews(0L);
            return EventMapperNew.mapToEventDto(eventRepository.saveAndFlush(event));
        } else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public EventDto getPublishedEventById(long eventId, Integer views) {
        Optional<Event> event = eventRepository.findByIdAndState(eventId, "PUBLISHED");
        if (event.isPresent()) {
            Event updated = event.get();
            updated.setViews(Long.valueOf(views));
            updated = eventRepository.saveAndFlush(updated);
            return EventMapperNew.mapToEventDto(updated);
        } else throw new EntityNotFoundException();
    }

    @Override
    public Collection<ResponseEventDto> findEventsByUser(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<ResponseEventDto> result = new ArrayList<>();
        List<Event> listOfEvents = new ArrayList<>();
        if (onlyAvailable) {
            listOfEvents.addAll(eventRepository.findByPaidAndEventDateGreaterThanEqualAndEventDateLessThanEqualAndParticipantLimitGreaterThanEqual(paid, rangeStart, rangeEnd, 0L, page));
        } else {
            listOfEvents.addAll(eventRepository.findByPaidAndEventDateGreaterThanEqualAndEventDateLessThanEqualAndParticipantLimit(paid, rangeStart, rangeEnd, 0L, page));
        }
        for (Event event : listOfEvents) {
            if (!text.equals(" ") || !text.isBlank()) {
                if (!categories.equals(List.of(0L)))
                    if ((event.getAnnotation().contains(text) || event.getDescription().contains(text)) && categories.contains(event.getCategory().getId()))
                        result.add(EventMapperNew.mapToResponseEventDto(event));
                    else {
                        if (event.getAnnotation().contains(text) || event.getDescription().contains(text))
                            result.add(EventMapperNew.mapToResponseEventDto(event));
                    }
            } else {
                if (!categories.equals(List.of(0L))) if (categories.contains(event.getCategory().getId()))
                    result.add(EventMapperNew.mapToResponseEventDto(event));
                else {
                    result.add(EventMapperNew.mapToResponseEventDto(event));
                }
            }
        }
        if (sort.equals("VIEWS")) result.sort(Comparator.comparing(ResponseEventDto::getViews).reversed());
        if (sort.equals("EVENT_DATE")) result.sort(Comparator.comparing(ResponseEventDto::getEventDate).reversed());
        return result;
    }

    @Override
    @Transactional
    public EventDto changeEventByAdmin(long eventId, PatchEventDto patchEventDto) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            Optional<Category> category;
            Category cat = null;
            if (patchEventDto.getCategory() != null) {
                category = categoryRepository.findById(patchEventDto.getCategory());
                if (category.isPresent()) cat = category.get();
            }
            Location location = null;
            if (patchEventDto.getLocation() != null)
                location = locationRepository.saveAndFlush(EventMapperNew.mapToLocation(patchEventDto.getLocation()));
            String state = event.get().getState();
            Event newEvent = EventMapperNew.changeEvent(event.get(), patchEventDto);
            if (cat != null) newEvent.setCategory(cat);
            if (location != null) newEvent.setLocation(location);
            if (patchEventDto.getStateAction() != null) {
                switch (patchEventDto.getStateAction()) {
                    case "REJECT_EVENT": {
                        if (state.equals("PUBLISHED")) throw new DataIntegrityViolationException("Data integrity violation exception");
                        newEvent.setState(CANCELED.toString().toUpperCase());
                        return EventMapperNew.mapToEventDto(eventRepository.saveAndFlush(newEvent));
                    }
                    case "PUBLISH_EVENT": {
                        if (state.equals("PUBLISHED") || state.equals("CANCELED"))
                            throw new DataIntegrityViolationException("Data integrity violation exception");
                        newEvent.setState("PUBLISHED");
                        newEvent.setPublishedOn(LocalDateTime.now());
                        return EventMapperNew.mapToEventDto(eventRepository.saveAndFlush(newEvent));
                    }
                    default:
                }
            } else newEvent.setState(state);
            return EventMapperNew.mapToEventDto(eventRepository.saveAndFlush(newEvent));
        } else throw new EntityNotFoundException();
    }

    @Override
    public Collection<EventDto> findEventsByAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<EventDto> result = new ArrayList<>();
        List<Event> listOfEvents = new ArrayList<>(eventRepository.findByEventDateGreaterThanEqualAndEventDateLessThanEqual(rangeStart, rangeEnd, page));
        for (Event event : listOfEvents) {
            if (!users.equals(List.of(0L))) {
                if (!states.equals(List.of("0"))) {
                    if (!categories.equals(List.of(0L)))
                        if (users.contains(event.getInitiator().getId()) && states.contains(event.getState()) && categories.contains(event.getCategory().getId()))
                            result.add(EventMapperNew.mapToEventDto(event));
                        else if (users.contains(event.getInitiator().getId()) && states.contains(event.getState()))
                            result.add(EventMapperNew.mapToEventDto(event));
                } else if (!categories.equals(List.of(0L)))
                    if (users.contains(event.getInitiator().getId()) && categories.contains(event.getCategory().getId()))
                        result.add(EventMapperNew.mapToEventDto(event));
                    else if (users.contains(event.getInitiator().getId()))
                        result.add(EventMapperNew.mapToEventDto(event));
            } else if (!states.equals(List.of("0"))) {
                if (!categories.equals(List.of(0L)))
                    if (states.contains(event.getState()) && categories.contains(event.getCategory().getId()))
                        result.add(EventMapperNew.mapToEventDto(event));
                    else if (states.contains(event.getState())) result.add(EventMapperNew.mapToEventDto(event));
            } else if (!categories.equals(List.of(0L))) {
                if (categories.contains(event.getCategory().getId())) result.add(EventMapperNew.mapToEventDto(event));
            } else result.add(EventMapperNew.mapToEventDto(event));
        }
        return result;
    }
}