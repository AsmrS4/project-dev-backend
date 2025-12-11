package com.project.backend.dto.event;

import com.project.backend.enums.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class EventDto {
    private UUID id;
    private String title;
    private String description;
    private String address;
    private EventStatus status;
    private LocalDateTime dateTime;
    private LocalDateTime createTime;
    private List<ImageDto> images;
}
