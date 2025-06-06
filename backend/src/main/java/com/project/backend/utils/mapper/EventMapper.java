package com.project.backend.utils.mapper;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.entities.event.Event;

import java.util.UUID;

public class EventMapper {
    public Event map(EventCreateDto createDto, UUID id) {
        Event event = new Event();
        event.setId(id);
        event.setTitle(createDto.getTitle());
        event.setDescription(createDto.getDescription());
        event.setAddress(createDto.getAddress());
        event.setDateTime(createDto.getDateTime());
        event.setImages(createDto.getImages());

        return event;
    }

    public EventDto map(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDescription(event.getDescription());
        eventDto.setAddress(event.getAddress());
        eventDto.setDateTime(event.getDateTime());
        eventDto.setCreateTime(event.getCreateTime());
        eventDto.setImages(event.getImages());

        return eventDto;
    }
}
