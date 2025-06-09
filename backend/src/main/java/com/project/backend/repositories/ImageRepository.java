package com.project.backend.repositories;

import com.project.backend.entities.event.Image;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    @Modifying
    @Query("SELECT i FROM Image i WHERE i.event.id = :eventId")
    List<Image> getImages(@Param("eventId") UUID eventId);
}
