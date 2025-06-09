package com.project.backend.services.event;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;

import java.util.List;
import java.util.UUID;

public interface EventService {
    UUID createEvent(EventCreateDto createDto);
    EventDto getEventDetails(UUID id);
    List<EventDto> getEvents();

}
