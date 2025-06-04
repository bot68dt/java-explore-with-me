package ru.practicum.explore.event.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.practicum.explore.event.dto.EventDto;
import ru.practicum.explore.event.dto.PatchEventDto;
import ru.practicum.explore.event.dto.ResponseEventDto;
import ru.practicum.explore.event.service.EventService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Request to get event with ID {} of user with ID {} received.", eventId, userId);
        return ResponseEntity.ok().body(eventService.getEventById(userId, eventId));
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDto> getPublishedEventById(@PathVariable long id, @RequestParam(name = "views") Integer views) {
        log.info("Request to get published event with ID {} received.", id);
        return ResponseEntity.ok().body(eventService.getPublishedEventById(id, views));
    }

    @GetMapping("/users/{userId}/events")
    public ResponseEntity<Collection<ResponseEventDto>> getEvents(@PathVariable long userId, @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get events of the user received.");
        return ResponseEntity.ok().body(eventService.getAllUserEvents(userId, from, size));
    }

    @GetMapping("/admin/events")
    public ResponseEntity<Collection<EventDto>> findEventsByAdmin(@RequestParam(name = "users") List<Long> users, @RequestParam(name = "states") List<String> states, @RequestParam(name = "categories") List<Long> categories, @PastOrPresent @RequestParam(name = "rangeStart") LocalDateTime rangeStart, @FutureOrPresent @RequestParam(name = "rangeEnd") LocalDateTime rangeEnd, @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get events by Admin received.");
        //if(categories==(null)) categories = List.of(1L);
        return ResponseEntity.ok().body(eventService.findEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @GetMapping("/events")
    public ResponseEntity<Collection<ResponseEventDto>> findEventsByUser(@RequestParam(required = false, name = "text") String text, @RequestParam(required = false, name = "categories") List<Long> categories, @RequestParam(required = false, name = "paid") Boolean paid, @PastOrPresent @RequestParam(required = false, name = "rangeStart") LocalDateTime rangeStart, @FutureOrPresent @RequestParam(required = false, name = "rangeEnd") LocalDateTime rangeEnd, @RequestParam(required = false, name = "onlyAvailable") Boolean onlyAvailable, @RequestParam(required = false, name = "sort") String sort, @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get events by User received.");
        //if(categories==(null)) categories = List.of(1L);
        return ResponseEntity.ok().body(eventService.findEventsByUser(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size));
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public ResponseEntity<EventDto> changeEvent(@PathVariable long userId, @PathVariable long eventId, @RequestBody PatchEventDto patchEventDto) {
        log.info("Request to change event {} of user with ID {} received.", eventId, userId);
        return ResponseEntity.ok().body(eventService.changeEvent(userId, eventId, patchEventDto));
    }

    @PatchMapping("/admin/events/{eventId}")
    public ResponseEntity<EventDto> changeEventByAdmin(@PathVariable long eventId, @RequestBody PatchEventDto patchEventDto) {
        log.info("Request to change event with ID {} received.", eventId);
        return ResponseEntity.ok().body(eventService.changeEventByAdmin(eventId, patchEventDto));
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<EventDto> createEvent(@PathVariable long userId, @RequestBody PatchEventDto newEventDto) throws IOException {
        log.info("Request to create new event received: {}", newEventDto);
        EventDto event = eventService.createEvent(userId, newEventDto);
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.findAndRegisterModules();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = objMapper.getFactory().createGenerator(byteArrayOutputStream);
        objMapper.writeValue(jsonGenerator, event);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(byteArrayOutputStream.size()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
        log.info("New user created with ID {}", event.getId());
        return ResponseEntity.created(location).headers(httpHeaders).body(event);
    }
}