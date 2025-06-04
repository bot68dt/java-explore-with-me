package ru.practicum.explore.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.user.client.AdminUserClient;
import ru.practicum.explore.user.client.UserClient;
import ru.practicum.explore.user.dto.RequestsDto;
import ru.practicum.explore.user.dto.UserDto;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final AdminUserClient adminUserClient;
    private final UserClient userClient;

    @GetMapping("/users/{userId}/requests")
    public ResponseEntity<Object> getRequsets(@Valid @PathVariable long userId) {
        log.info("Request to get requests of user with ID {} received.", userId);
        return userClient.getUserRequests(userId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getEventRequsets(@Valid @PathVariable long userId, @PathVariable long eventId) {
        log.info("Request to get requests of the event with ID {} received.", userId);
        return userClient.getEventRequests(userId, eventId);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Object> getUsers(@Valid @RequestParam(required = false, name = "ids", defaultValue = "0") Long ids, @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get users received.");
        return adminUserClient.getAllUsers(ids, from, size);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequest(@Valid @PathVariable long userId, @PathVariable long requestId) {
        log.info("Request to cancel request {} of user with ID {} received.", requestId, userId);
        return userClient.cancelRequest(userId, requestId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> changeRequestsStatuses(@Valid @PathVariable long userId, @PathVariable long eventId, @RequestBody RequestsDto requestsDto) {
        log.info("Request to change requests of user with ID {} received.", userId);
        return userClient.changeRequestsStatuses(userId, eventId, requestsDto);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Object> deleteUser(@Valid @PathVariable long userId) {
        log.info("Request to delete user with ID {} received.", userId);
        return adminUserClient.deleteUser(userId);
    }

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<Object> createRequest(@Valid @PathVariable long userId, @RequestParam(name = "eventId") long eventId) {
        log.info("Request to create new request from user with ID received: {}", userId);
        return userClient.createRequest(userId, eventId);
    }

    @PostMapping(value = "/admin/users", produces = "application/json")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Request to create new user received: {}", userDto);
        return new ResponseEntity<Object>(adminUserClient.createUser(userDto), httpHeaders, HttpStatus.OK);
    }
}