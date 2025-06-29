package com.project.backend.utils.schedule;

import com.project.backend.entities.booking.Booking;
import com.project.backend.entities.event.Event;
import com.project.backend.enums.EventStatus;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.EventRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class EventScheduler {
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(EventScheduler.class);
    @PostConstruct
    public void init() {
        archiveEvent();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleTask() {
        archiveEvent();
    }

    public void archiveEvent() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = eventRepository.getActiveEvents();
        for (Event event : events) {
            if (event.getDateTime().isBefore(now)) {
                event.setStatus(EventStatus.ARCHIVED);
                eventRepository.save(event);
                List<Booking> bookings = bookingRepository.getEventsBooking(event.getId());
                for(Booking booking: bookings) {
                    booking.setStatus(EventStatus.ARCHIVED);
                    bookingRepository.save(booking);
                }
                LOGGER.info("Mark event with id: {} as archived", event.getId());
            }
        }
    }
}
