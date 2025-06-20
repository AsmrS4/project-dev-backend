package com.project.backend.controllers;

import com.project.backend.services.history.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;
    @GetMapping("/event")
    public ResponseEntity<?> getEventsHistory() {
        return null;
    }
    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventHistoryDetails(@PathVariable UUID id) {
        return null;
    }

    @GetMapping("/event/{id}/reviews")
    public ResponseEntity<?> getEventReviews(@PathVariable UUID id) {
        return null;
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> getVisitedEvents() {
        return null;
    }

    @PostMapping("/event/{id}/review")
    public ResponseEntity<?> createReview() {
        return null;
    }

}
