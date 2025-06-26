package com.project.backend.utils.mapper;

import com.project.backend.dto.booking.BookingDto;
import com.project.backend.entities.booking.Booking;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {
    public BookingDto map(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUserId());
        dto.setEventId(booking.getEvent().getId());
        dto.setStatus(booking.getStatus());
        return dto;
    }
    public List<BookingDto> map(List<Booking> bookings) {
        return bookings.stream().map(this::map).collect(Collectors.toList());
    }
}
