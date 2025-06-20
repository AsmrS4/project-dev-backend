package com.project.backend.services.history;

import com.project.backend.dto.booking.BookingDto;
import com.project.backend.dto.event.EventDto;
import com.project.backend.dto.review.ReviewDto;
import com.project.backend.dto.review.ReviewRequest;
import com.project.backend.entities.booking.Booking;
import com.project.backend.entities.event.Event;
import com.project.backend.entities.event.Image;
import com.project.backend.enums.EventStatus;
import com.project.backend.exceptions.BadRequestException;
import com.project.backend.exceptions.UnauthorizedException;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.EventRepository;
import com.project.backend.repositories.ImageRepository;
import com.project.backend.services.booking.BookingService;
import com.project.backend.utils.mapper.BookingMapper;
import com.project.backend.utils.mapper.EventMapper;
import com.project.backend.utils.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private final BookingRepository bookingRepository;
    private final EventMapper eventMapper;
    private final ImageMapper imageMapper;
    private final BookingMapper bookingMapper;
    @Override
    public List<EventDto> getEventsHistory() {
        List<Event> events = eventRepository.getArchievedEvents();
        return events.stream().map(event -> {
            Event item = eventRepository.findEventById(event.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
            List<Image> images = imageRepository.getImages(event.getId());
            return eventMapper.map(item, imageMapper.map(images));
        }).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventHistoryDetails(UUID eventId) {
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        if(!event.getStatus().equals(EventStatus.ARCHIEVED)) {
            throw new BadRequestException("Событие еще активно");
        }
        List<Image> images = imageRepository.getImages(eventId);
        return eventMapper.map(event, imageMapper.map(images));
    }

    @Override
    public List<ReviewDto> getEventReviews() {
        return null;
    }

    @Override
    public List<BookingDto> getVisitedEvents() {
        List<Booking> bookings = bookingRepository.getVisitedBooking(UUID.fromString(getAuthId()));
        return bookingMapper.map(bookings);
    }

    @Override
    public UUID createReview(UUID reviewId, ReviewRequest request) {
        return null;
    }

    private String getAuthId() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if(id == null) {
            throw new UnauthorizedException("Authorization is required");
        }
        return id;
    }
}
