package com.project.backend.services.history;

import com.project.backend.dto.booking.BookingDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.review.ReviewRequest;
import com.project.backend.dto.review.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface HistoryService {
    List<EventDto> getEventsHistory();
    EventDto getEventHistoryDetails(UUID eventId);
    ReviewResponse getEventReviews(UUID eventId);
    List<BookingDto> getVisitedEvents();

    UUID createReview(UUID eventId, ReviewRequest request);
}
