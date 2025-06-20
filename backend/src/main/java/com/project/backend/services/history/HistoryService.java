package com.project.backend.services.history;

import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.review.ReviewDto;

import java.util.List;
import java.util.UUID;

public interface HistoryService {
    List<EventDto> getEventsHistory();
    EventDto getEventHistoryDetails(UUID eventId);
    List<ReviewDto> getEventReviews();
    List<>
}
