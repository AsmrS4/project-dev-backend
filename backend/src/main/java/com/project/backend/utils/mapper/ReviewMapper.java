package com.project.backend.utils.mapper;

import com.project.backend.dto.review.ReviewDto;
import com.project.backend.dto.review.ReviewRequest;
import com.project.backend.entities.auth.User;
import com.project.backend.entities.review.Review;
import com.project.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReviewMapper {
    @Autowired
    private UserRepository userRepository;
    public Review map(UUID generatedId, UUID eventId, UUID authorId, ReviewRequest request) {
        Review review = new Review();
        review.setId(generatedId);
        review.setId(generatedId);
        review.setEventId(eventId);
        review.setAuthorId(authorId);
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        return review;
    }
    public ReviewDto map(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        User user = userRepository.findUserById(review.getAuthorId());
        reviewDto.setId(review.getId());
        reviewDto.setAuthorId(review.getAuthorId());
        reviewDto.setFullName(user.getFullName());
        reviewDto.setImageUrl(user.getImage());
        reviewDto.setEventId(review.getEventId());
        reviewDto.setContent(review.getContent());
        reviewDto.setRating(review.getRating());
        reviewDto.setCreateTime(review.getCreateTime());

        return reviewDto;
    }
    public List<ReviewDto> map(List<Review> reviews) {
        return reviews.stream().map(this::map).collect(Collectors.toList());
    }
}
