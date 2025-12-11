package com.project.backend.repositories;

import com.project.backend.dto.user.UserCardDto;
import com.project.backend.entities.booking.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    @Query("SELECT b FROM Booking b WHERE b.userId = :userId AND b.status = 0")
    List<Booking> getActiveBooking(@Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query("SELECT b FROM Booking b WHERE b.userId = :userId AND (b.status = 2 OR b.status = 1)")
    List<Booking> getVisitedBooking(@Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query("SELECT b FROM Booking b WHERE b.event.id = :eventId")
    List<Booking> getEventsBooking(@Param("eventId") UUID eventId);

    @Query("SELECT u.email FROM Booking b JOIN User u ON b.userId = u.id WHERE b.event.id = :eventId")
    List<String> getSubscribersEmails(@Param("eventId") UUID eventId);

    @Transactional
    @Query("SELECT b FROM Booking b WHERE b.event.id =:eventId AND b.userId =:userId AND b.status = 0")
    Optional<Booking> findBooking(@Param("eventId") UUID eventId, @Param("userId") UUID userId);

    @Query("SELECT new com.project.backend.dto.user.UserCardDto(u.fullName, u.id, u.image, u.role) FROM Booking b JOIN User u ON b.userId = u.id WHERE b.event.id = :eventId AND b.status = 0")
    List<UserCardDto> findSubscribersByEventId(@Param("eventId") UUID eventId);
}
