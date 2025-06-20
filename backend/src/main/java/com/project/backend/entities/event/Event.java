package com.project.backend.entities.event;

import com.project.backend.entities.booking.Booking;
import com.project.backend.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name="title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private EventStatus status = EventStatus.ACTIVE;
    @Column(name="date")
    private LocalDateTime dateTime;
    @Column(name="createTime")
    private LocalDateTime createTime = LocalDateTime.now();
    @Column(name="updateTime")
    private LocalDateTime updateTime = null;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
