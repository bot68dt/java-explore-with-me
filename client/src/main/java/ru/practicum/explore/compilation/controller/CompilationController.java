package ru.practicum.explore.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.compilation.client.AdminCompilationClient;
import ru.practicum.explore.compilation.client.CompilationClient;
import ru.practicum.explore.compilation.dto.CompilationDto;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class CompilationController {

    private final CompilationClient compilationClient;
    private final AdminCompilationClient adminCompilationClient;

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<Object> getCompilation(@Valid @PathVariable long compId) {
        log.info("Request to get compilation with ID {} received.", compId);
        return compilationClient.getCompilation(compId);
    }

    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(@Valid @RequestParam(required = false, name = "pinned", defaultValue = "") String pinned, @RequestParam(required = false, name = "from", defaultValue = "0") Integer from, @RequestParam(required = false, name = "size", defaultValue = "10") Integer size) {
        log.info("Request to get compilations received.");
        return compilationClient.getCompilations(pinned, from, size);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> changeCompilation(@Valid @PathVariable long compId, @Valid @RequestBody CompilationDto compilationDto) {
        log.info("Request to change compilation with ID {} received.", compId);
        return adminCompilationClient.changeCompilation(compId, compilationDto);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public ResponseEntity<Object> deleteCompilation(@Valid @PathVariable long compId) {
        log.info("Request to delete compilation with ID {} received.", compId);
        return adminCompilationClient.deleteCompilation(compId);
    }

    @PostMapping("/admin/compilations")
    public ResponseEntity<Object> createCompilation(@Valid @RequestBody CompilationDto compilationDto) {
        log.info("Request to create new compilation received: {}", compilationDto);
        return adminCompilationClient.createCompilation(compilationDto);
    }
}