package com.project.backend.services.event;

import com.project.backend.dto.event.EventCreateDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.event.EventUpdateDto;
import com.project.backend.dto.event.ImageCreateDto;
import com.project.backend.dto.user.UserCardDto;
import com.project.backend.entities.booking.Booking;
import com.project.backend.entities.event.Event;
import com.project.backend.entities.event.Image;
import com.project.backend.enums.EventStatus;
import com.project.backend.exceptions.BadRequestException;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.EventRepository;
import com.project.backend.repositories.ImageRepository;
import com.project.backend.services.sender.EmailSenderService;
import com.project.backend.utils.mapper.EventMapper;
import com.project.backend.utils.mapper.ImageMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private final BookingRepository bookingRepository;
    private final EmailSenderService emailSenderService;
    private final EventMapper eventMapper;
    private final ImageMapper imageMapper;
    @Override
    public UUID createEvent(EventCreateDto createDto) throws MessagingException {
        Event event = eventMapper.map(createDto, generateUUID());
        eventRepository.save(event);
        List<ImageCreateDto> images = createDto.getImages();
        for(ImageCreateDto imageCreateDto: images) {
            Image newImage = new Image();
            newImage.setImageUrl(imageCreateDto.getImageUrl());
            newImage.setEvent(event);
            imageRepository.save(newImage);
        }
        emailSenderService.sendNewEventNotification(event);
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
    public UUID editEventDetails(UUID id, EventUpdateDto updateDto) throws MessagingException {
        Event event = eventRepository.findEventById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        List<Image> images = imageRepository.getImages(id);
        for(Image image: images) {
            imageRepository.delete(image);
        }
        List<ImageCreateDto> newImages = updateDto.getImages();
        for(ImageCreateDto imageCreateDto: newImages) {
            Image newImage = new Image();
            newImage.setImageUrl(imageCreateDto.getImageUrl());
            newImage.setEvent(event);
            imageRepository.save(newImage);
        }
        if(!event.getDateTime().equals(updateDto.getDateTime())) {
            emailSenderService.sendEventDateChangeNotification(event, updateDto);
        }
        eventRepository.save(eventMapper.map(event, updateDto));
        return event.getId();
    }

    @Override
    public Object cancelEvent(UUID id) throws MessagingException {
        Event event = eventRepository.findEventById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        if(!event.getStatus().equals(EventStatus.ACTIVE)) {
            throw new BadRequestException("Event status isn't active");
        }
        event.setStatus(EventStatus.CANCELED);
        emailSenderService.sendEventChangesNotification(event);
        eventRepository.save(event);
        List<Booking> bookingList = bookingRepository.getEventsBooking(id);
        for(Booking booking : bookingList) {
            booking.setStatus(EventStatus.CANCELED);
            bookingRepository.save(booking);
        }

        return true;
    }

    @Override
    public List<EventDto> getEvents(LocalDateTime startDate, LocalDateTime endDate) {

        List<Event> events = eventRepository.getActiveEventsBetweenDates(startDate, endDate);

        return events.stream().map(event -> {
            Event item = eventRepository.findEventById(event.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
            List<Image> images = imageRepository.getImages(event.getId());
            return eventMapper.map(item, imageMapper.map(images));
        }).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getArchievedEvents() {
        List<Event> archive = eventRepository.getArchievedEvents();
        return archive.stream().map(event -> {
            Event item = eventRepository.findEventById(event.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
            List<Image> images = imageRepository.getImages(event.getId());
            return eventMapper.map(item, imageMapper.map(images));
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserCardDto> getGuests(UUID eventId) {
        return bookingRepository.findSubscribersByEventId(eventId);
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }
}
