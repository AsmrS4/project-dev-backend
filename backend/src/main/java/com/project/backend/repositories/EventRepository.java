package com.project.backend.repositories;

import com.project.backend.entities.event.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends CrudRepository<Event, UUID> {
    Optional<Event> findEventById(UUID id);
    @Modifying
    @Query("SELECT e FROM Event e")
    List<Event> getEvents();
}
