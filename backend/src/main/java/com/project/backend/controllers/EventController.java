package com.project.backend.controllers;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.services.event.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping
    public ResponseEntity<?> getEvents() {
        return ResponseEntity.ok(eventService.getEvents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventDetails(@PathVariable UUID id){
        return ResponseEntity.ok(eventService.getEventDetails(id));
    }
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventCreateDto createDto) {
        return ResponseEntity.ok(eventService.createEvent(createDto));
    }

}
