package com.project.backend.repositories;

import com.project.backend.entities.event.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends CrudRepository<Event, UUID> {
    Optional<Event> findEventById(UUID id);
    @Modifying
    @Query("SELECT e FROM Event e")
    List<Event> getEvents();

    @Query("SELECT e FROM Event e WHERE e.status = 0 ORDER BY e.dateTime ASC")
    List<Event> getActiveEvents();

    @Query("SELECT e FROM Event e WHERE e.status = 0 " +
            "AND (cast(:startDate as timestamp) IS NULL OR e.dateTime >= :startDate) " +
            "AND (cast(:endDate as timestamp) IS NULL OR e.dateTime <= :endDate) " +
            "ORDER BY e.dateTime ASC")
    List<Event> getActiveEventsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Modifying
    @Query("SELECT e FROM Event e WHERE e.status = 2 ORDER BY e.dateTime ASC")
    List<Event> getArchievedEvents();
}
