package com.project.backend.entities.booking;

import com.project.backend.enums.EventStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "eventId", columnDefinition = "uuid")
    private UUID eventId;
    @Column(name = "status")
    private EventStatus status = EventStatus.ACTIVE;
}
