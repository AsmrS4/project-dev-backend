package com.project.backend.services.booking;

import com.project.backend.dto.booking.BookingDto;
import com.project.backend.entities.booking.Booking;
import com.project.backend.entities.event.Event;
import com.project.backend.enums.EventStatus;
import com.project.backend.exceptions.BadRequestException;
import com.project.backend.exceptions.UnauthorizedException;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.EventRepository;
import com.project.backend.utils.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper mapper;
    @Override
    public UUID bookTicket(UUID eventId) {
        UUID userId = UUID.fromString(getAuthId());
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        Optional<Booking> book = bookingRepository.findBooking(eventId, userId);
        if(!event.getStatus().equals(EventStatus.ACTIVE)) {
            throw new BadRequestException("Event status isn't active");
        }
        if(book.isPresent() && book.get().getStatus() == EventStatus.ACTIVE) {
            throw new BadRequestException("You have already booked ticket on this event");
        }

        Booking booking = new Booking();
        booking.setId(generateUUID());
        booking.setUserId(userId);
        booking.setEvent(event);

        bookingRepository.save(booking);
        return booking.getId();
    }

    @Override
    public UUID cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(()-> new UsernameNotFoundException("Booking doesn't exist"));
        if(!booking.getStatus().equals(EventStatus.ACTIVE)) {
            throw new BadRequestException("Booking already cancelled");
        }
        booking.setStatus(EventStatus.CANCELED);
        bookingRepository.save(booking);
        return bookingId;
    }

    @Override
    public List<BookingDto> getUserBookings() {
        List<Booking> bookings = bookingRepository.getActiveBooking(UUID.fromString(getAuthId()));
        return mapper.map(bookings);
    }

    @Override
    public BookingDto getBookingDetails(UUID bookingId) {
        Optional<Booking> booking = bookingRepository.findBookingById(bookingId);
        if(booking.isEmpty()) {
            throw new BadRequestException("Booking with id " + bookingId + "wasn't found");
        }
        return mapper.map(booking.get());
    }

    @Override
    public boolean checkHasActiveBooking(UUID eventId) {
        UUID userId = UUID.fromString(getAuthId());
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        Optional<Booking> book = bookingRepository.findBooking(eventId, userId);
        return book.isPresent();
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }

    private String getAuthId() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if(id == null) {
            throw new UnauthorizedException("Authorization is required");
        }
        return id;
    }
}
