package com.project.backend.services.booking;

import com.project.backend.entities.booking.Booking;

import java.util.UUID;

public interface BookingService {
    UUID bookTicket(UUID eventId);
    UUID cancelBooking(UUID bookingId);
    Booking getBookingDetails(UUID bookingId);
}
