package com.project.backend.services.event;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.event.EventUpdateDto;

import java.util.List;
import java.util.UUID;

public interface EventService {
    UUID createEvent(EventCreateDto createDto);
    EventDto getEventDetails(UUID id);
    UUID editEventDetails(UUID id, EventUpdateDto updateDto);
    List<EventDto> getEvents();
    Object cancelEvent(UUID id);
    List<EventDto> getArchievedEvents();
}
