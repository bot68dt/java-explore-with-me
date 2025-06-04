package ru.practicum.explore.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.practicum.explore.user.dto.*;
import ru.practicum.explore.user.service.UserService;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<Collection<RequestDto>> getRequsets(@PathVariable long userId) {
        log.info("Request to get requests of user with ID {} received.", userId);
        return ResponseEntity.ok().body(userService.getUserRequests(userId));
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Collection<RequestDto>> getEventRequsets(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Request to get requests of the event with ID {} received.", userId);
        return ResponseEntity.ok().body(userService.getEventRequests(userId, eventId));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Collection<UserDto>> getUsers(@RequestParam(required = false, name = "ids") List<Long> ids, @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get users received.");
        return ResponseEntity.ok().body(userService.getAllUsers(ids, from, size));
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<RequestDto> cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
        log.info("Request to cancel request {} of user with ID {} received.", requestId, userId);
        return ResponseEntity.ok().body(userService.cancelRequest(userId, requestId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Collection<RequestDto>> changeRequestsStatuses(@PathVariable long userId, @PathVariable long eventId, @RequestBody ChangedStatusOfRequestsDto changedStatusOfRequestsDto) {
        log.info("Request to change requests of user with ID {} received.", userId);
        return ResponseEntity.ok().body(userService.changeRequestsStatuses(userId, eventId, changedStatusOfRequestsDto));
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        log.info("Request to delete user with ID {} received.", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<RequestDto> createRequest(@PathVariable long userId, @RequestParam(required = false, name = "eventId", defaultValue = "0") long eventId) {
        log.info("Request to create new request from user with ID received: {}", userId);
        RequestDto request = userService.createRequest(userId, eventId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(request.getId()).toUri();
        log.info("New request created with ID {}", request.getId());
        return ResponseEntity.created(location).body(request);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<UserDto> createUser(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email) {
        UserDto userDto = new UserDto(null, name, email);
        //log.info("Request to create new user received: {}", userDto);
        UserDto user = userService.createUser(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        //log.info("New user created with ID {}", user.getId());
        return ResponseEntity.created(location).header(null).body(user);
    }
}