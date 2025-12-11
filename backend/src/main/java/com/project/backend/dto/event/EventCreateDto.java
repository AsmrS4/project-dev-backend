package com.project.backend.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventCreateDto {
    @NotBlank(message = "Требуется название")
    private String title;
    @NotBlank(message = "Требуется описание")
    private String description;
    @NotBlank(message = "Требуется адрес")
    private String address;
    @NotNull(message = "Укажите дату проведения")
    private LocalDateTime dateTime;
    @NotNull(message = "Укажите хотя бы одно изображение")
    @Size(min = 1, message = "Укажите хотя бы одно изображение")
    private List<ImageCreateDto> images;
}
