package com.project.backend.dto.event;

import com.project.backend.entities.event.Event;
import lombok.Data;

import java.util.List;

@Data
public class ImageCreateDto {
    private String imageUrl;
}
