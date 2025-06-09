package com.project.backend.services.event;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.event.ImageCreateDto;
import com.project.backend.entities.event.Event;
import com.project.backend.entities.event.Image;
import com.project.backend.repositories.EventRepository;
import com.project.backend.repositories.ImageRepository;
import com.project.backend.utils.mapper.EventMapper;
import com.project.backend.utils.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private final EventMapper eventMapper;
    private final ImageMapper imageMapper;
    @Override
    public UUID createEvent(EventCreateDto createDto) {
        Event event = eventMapper.map(createDto, generateUUID());
        eventRepository.save(event);
        List<ImageCreateDto> images = createDto.getImages();
        for(ImageCreateDto imageCreateDto: images) {
            Image newImage = new Image();
            newImage.setImageUrl(imageCreateDto.getImageUrl());
            newImage.setEvent(event);
            imageRepository.save(newImage);
        }

        return event.getId();
    }
    @Override
    public EventDto getEventDetails(UUID id) {
        Event event = eventRepository.findEventById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        List<Image> images = imageRepository.getImages(id);
        return eventMapper.map(event, imageMapper.map(images));
    }
    @Override
    public List<EventDto> getEvents() {
        List<Event> events = eventRepository.getEvents();
        List<EventDto> eventDtos = new ArrayList<>();
        eventDtos = events.stream().map(event -> {
            Event item = eventRepository.findEventById(event.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
            List<Image> images = imageRepository.getImages(event.getId());
            return eventMapper.map(item, imageMapper.map(images));
        }).collect(Collectors.toList());
        return eventDtos;
    }
    private UUID generateUUID() {
        return UUID.randomUUID();
    }
}
