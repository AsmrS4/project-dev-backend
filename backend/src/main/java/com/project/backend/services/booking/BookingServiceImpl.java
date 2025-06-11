package com.project.backend.services.booking;

import com.project.backend.entities.booking.Booking;
import com.project.backend.enums.EventStatus;
import com.project.backend.exceptions.BadRequestException;
import com.project.backend.exceptions.UnauthorizedException;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.EventRepository;
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
    @Override
    public UUID bookTicket(UUID eventId) {
        UUID userId = UUID.fromString(getAuthId());
        eventRepository.findEventById(eventId)
                .orElseThrow(()-> new UsernameNotFoundException("Событие не найдено"));
        Optional<Booking> book = bookingRepository.findBooking(eventId, userId);
        if(book.isPresent() && book.get().getStatus() != EventStatus.CANCELED) {
            throw new BadRequestException("You have already booked ticket on this event");
        }
        Booking booking = new Booking();
        booking.setId(generateUUID());
        booking.setUserId(userId);
        booking.setEventId(eventId);

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
    public List<Booking> getUserBookings() {
        return null;
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
