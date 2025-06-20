package com.project.backend.entities.booking;

import com.project.backend.entities.event.Event;
import com.project.backend.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;
    @Column(name = "userId", columnDefinition = "uuid")
    private UUID userId;
    @Column(name = "status")
    private EventStatus status = EventStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private Event event;
}
