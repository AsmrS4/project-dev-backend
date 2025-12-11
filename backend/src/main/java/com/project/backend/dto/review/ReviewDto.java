package com.project.backend.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ReviewDto {
    private UUID id;
    private UUID authorId;
    private UUID eventId;
    private String fullName;
    private String imageUrl;
    private String content;
    private int rating;
    private LocalDateTime createTime;
}
