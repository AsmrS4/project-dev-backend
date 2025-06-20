package com.project.backend.dto.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {
    private Long id;
    private String imageUrl;
    private UUID eventId;
}
