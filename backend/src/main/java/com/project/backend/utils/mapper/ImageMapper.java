package com.project.backend.utils.mapper;

import com.project.backend.dto.event.ImageDto;
import com.project.backend.entities.event.Image;

import java.util.List;
import java.util.stream.Collectors;

public class ImageMapper {
    private ImageDto map(Image image) {
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setImageUrl(image.getImageUrl());
        dto.setEventId(image.getEvent().getId());
        return dto;
    }
    public List<ImageDto> map(List<Image> images) {
        return images.stream().map(this::map).collect(Collectors.toList());
    }

}
