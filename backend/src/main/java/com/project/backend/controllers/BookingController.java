package com.project.backend.controllers;

import com.project.backend.services.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
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

    @GetMapping("/tickets")
    public ResponseEntity<List<?>> getAllActiveBooking(){
        return null;
    }
}
