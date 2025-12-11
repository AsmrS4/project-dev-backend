package com.project.backend.controllers;

import com.project.backend.services.booking.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
@Tag(name = "Booking", description = "Эндпоинты для бронирования билетов")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/{eventId}")
    public ResponseEntity<?> bookTicket(@PathVariable UUID eventId) {
        return ResponseEntity.ok(bookingService.bookTicket(eventId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingDetails(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(bookingService.getBookingDetails(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

    @GetMapping("/check/{eventId}")
    public ResponseEntity<?> checkHasActiveBooking(@PathVariable UUID eventId) {
        return ResponseEntity.ok(bookingService.checkHasActiveBooking(eventId));
    }
    @GetMapping("/tickets")
    public ResponseEntity<List<?>> getAllActiveBooking(){
        return ResponseEntity.ok(bookingService.getUserBookings());
    }
}
