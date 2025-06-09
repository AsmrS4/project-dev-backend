package com.project.backend.services.booking;

import com.project.backend.entities.booking.Booking;
import com.project.backend.entities.event.Event;
import com.project.backend.enums.EventStatus;
import com.project.backend.exceptions.UnauthorizedException;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    @Override
    public UUID bookTicket(UUID eventId) {
        eventRepository.findEventById(eventId)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        Booking booking = new Booking();
        booking.setId(generateUUID());
        booking.setUserId(UUID.fromString(getAuthId()));
        booking.setEventId(eventId);

        bookingRepository.save(booking);
        return booking.getId();
    }

    @Override
    public UUID cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(()-> new UsernameNotFoundException("Booking doesn't exist"));
        booking.setStatus(EventStatus.CANCELED);
        //TODO:здесь будет рассылка на почту об изменении статуса
        return bookingId;
    }

    @Override
    public Booking getBookingDetails(UUID bookingId) {
        return null;
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
