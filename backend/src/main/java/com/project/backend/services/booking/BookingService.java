package com.project.backend.services.booking;

import com.project.backend.dto.booking.BookingDto;
import com.project.backend.entities.booking.Booking;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    UUID bookTicket(UUID eventId);
    UUID cancelBooking(UUID bookingId);
    BookingDto getBookingDetails(UUID bookingId);
    List<BookingDto> getUserBookings();
}
