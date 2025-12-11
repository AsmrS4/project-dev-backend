package com.project.backend.entities.event;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private Event event;
}
