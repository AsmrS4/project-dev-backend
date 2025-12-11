package com.project.backend.dto.booking;

import com.project.backend.enums.EventStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class BookingDto {
    private UUID id;
    private UUID userId;
    private UUID eventId;
    private EventStatus status = EventStatus.ACTIVE;
}
