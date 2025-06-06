package com.project.backend.services.event;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.entities.event.Event;
import com.project.backend.repositories.EventRepository;
import com.project.backend.utils.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    @Override
    public UUID createEvent(EventCreateDto createDto) {
        Event event = eventMapper.map(createDto, generateUUID());
        eventRepository.save(event);
        return event.getId();
    }
    @Override
    public EventDto getEventDetails(UUID id) {
        Event event = eventRepository.findEventById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        return eventMapper.map(event);
    }
    @Override
    public List<EventDto> getEvents() {
        List<Event> events = eventRepository.getEvents();
        List<EventDto> eventDtos = new ArrayList<>();
        events.stream().map(event -> eventDtos.add(eventMapper.map(event)));
        return eventDtos;
    }
    private UUID generateUUID() {
        return UUID.randomUUID();
    }
}
