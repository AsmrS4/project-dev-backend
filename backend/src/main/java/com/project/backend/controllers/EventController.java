package com.project.backend.controllers;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.event.EventUpdateDto;
import com.project.backend.services.event.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Tag(name = "Events", description = "Эндпоинты для получения информации о мероприятиях")
public class EventController {
    private final EventService eventService;
    @GetMapping
    public ResponseEntity<?> getEvents(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(eventService.getEvents(startDate, endDate));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventDetails(@PathVariable UUID id){
        return ResponseEntity.ok(eventService.getEventDetails(id));
    }
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventCreateDto createDto) throws MessagingException {
        return ResponseEntity.ok(eventService.createEvent(createDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UUID> editEvent(@PathVariable UUID id, @RequestBody @Valid EventUpdateDto updateDto) throws MessagingException {
        return ResponseEntity.ok(eventService.editEventDetails(id, updateDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelEvent(@PathVariable UUID id) throws MessagingException {
        return ResponseEntity.ok(eventService.cancelEvent(id));
    }

    @GetMapping("/{id}/guests")
    public ResponseEntity<?> getEventGuests(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getGuests(id));
    }
}
