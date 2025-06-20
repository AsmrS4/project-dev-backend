package com.project.backend.entities.review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "authorId")
    private UUID authorId;
    @Column(name = "eventId")
    private UUID eventId;
    @Column(name="content")
    private String content;
    @Column(name="rating")
    private int rating;
    @Column(name = "createTime")
    private LocalDateTime createTime = LocalDateTime.now();
}
