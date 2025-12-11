package com.project.backend.utils.mapper;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.event.EventUpdateDto;
import com.project.backend.dto.event.ImageDto;
import com.project.backend.entities.event.Event;

import java.util.List;
import java.util.UUID;

public class EventMapper {
    public Event map(EventCreateDto createDto, UUID id) {
        Event event = new Event();
        event.setId(id);
        event.setTitle(createDto.getTitle());
        event.setDescription(createDto.getDescription());
        event.setAddress(createDto.getAddress());
        event.setDateTime(createDto.getDateTime());

        return event;
    }

    public Event map(Event event, EventUpdateDto updateDto){
        event.setTitle(updateDto.getTitle());
        event.setDescription(updateDto.getDescription());
        event.setAddress(updateDto.getAddress());
        event.setDateTime(updateDto.getDateTime());
        return event;
    }

    public EventDto map(Event event, List<ImageDto> images) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDescription(event.getDescription());
        eventDto.setAddress(event.getAddress());
        eventDto.setDateTime(event.getDateTime());
        eventDto.setCreateTime(event.getCreateTime());
        eventDto.setStatus(event.getStatus());
        eventDto.setImages(images);

        return eventDto;
    }

}
