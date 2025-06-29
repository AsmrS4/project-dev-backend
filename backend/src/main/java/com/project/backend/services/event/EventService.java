package com.project.backend.services.event;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.event.EventUpdateDto;
import com.project.backend.dto.user.UserCardDto;
import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventService {
    UUID createEvent(EventCreateDto createDto) throws MessagingException;
    EventDto getEventDetails(UUID id);
    UUID editEventDetails(UUID id, EventUpdateDto updateDto) throws MessagingException;
    List<EventDto> getEvents(LocalDateTime startDate, LocalDateTime endDate);
    Object cancelEvent(UUID id) throws MessagingException;
    List<EventDto> getArchievedEvents();
    List<UserCardDto> getGuests(UUID eventId);
}
