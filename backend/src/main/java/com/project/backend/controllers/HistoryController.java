package com.project.backend.controllers;

import com.project.backend.dto.review.ReviewRequest;
import com.project.backend.services.history.HistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@Tag(name = "History", description = "Эндпоинты для просмотра истории прошедших мероприятий")
public class HistoryController {
    private final HistoryService historyService;
    @GetMapping("/event")
    public ResponseEntity<?> getEventsHistory() {
        return ResponseEntity.ok(historyService.getEventsHistory());
    }
    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventHistoryDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(historyService.getEventHistoryDetails(id));
    }

    @GetMapping("/event/{id}/reviews")
    public ResponseEntity<?> getEventReviews(@PathVariable UUID id) {
        return ResponseEntity.ok(historyService.getEventReviews(id));
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> getVisitedEvents() {
        return ResponseEntity.ok(historyService.getVisitedEvents());
    }

    @PostMapping("/tickets/{id}/review")
    public ResponseEntity<?> createReview(@PathVariable UUID id, @RequestBody @Valid ReviewRequest request) {
        return ResponseEntity.ok(historyService.createReview(id, request));
    }

}
