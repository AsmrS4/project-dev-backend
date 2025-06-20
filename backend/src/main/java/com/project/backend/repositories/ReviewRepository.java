package com.project.backend.repositories;

import com.project.backend.entities.review.Review;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends CrudRepository<Review, UUID> {
    @Modifying
    @Query("SELECT r FROM Review r WHERE r.eventId =:eventId")
    List<Review> findEventReviews(@Param("eventId")UUID eventId);
}
