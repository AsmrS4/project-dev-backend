package com.project.backend.repositories;

import com.project.backend.entities.booking.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends CrudRepository<Booking, UUID> {
    Optional<Booking> findBookingById(UUID id);
    @Transactional
    @Modifying
    @Query("SELECT b FROM Booking b WHERE b.userId = :userId")
    List<Booking> getUsersBooking(@Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query("SELECT b FROM Booking b WHERE b.eventId = :eventId")
    List<Booking> getEventsBooking(@Param("eventId") UUID eventId);

    @Transactional
    @Query("SELECT b FROM Booking b WHERE b.eventId =:eventId AND b.userId =:userId")
    Optional<Booking> findBooking(@Param("eventId") UUID eventId, @Param("userId") UUID userId);
}
