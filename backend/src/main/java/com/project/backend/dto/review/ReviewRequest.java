package com.project.backend.dto.review;

import lombok.Data;

@Data
public class ReviewRequest {
    private String content;
    private int rating;
}
