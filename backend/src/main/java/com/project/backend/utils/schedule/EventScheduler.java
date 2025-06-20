package com.project.backend.utils.schedule;

import com.project.backend.entities.event.Event;
import com.project.backend.enums.EventStatus;
import com.project.backend.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final EventRepository eventRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(EventScheduler.class);
    @Scheduled(cron = "0 0 0 * * *")
    public void archieveEvent() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = eventRepository.getActiveEvents();
        for (Event event : events) {
            if (event.getDateTime().isBefore(now)) {
                event.setStatus(EventStatus.ARCHIEVED);
                eventRepository.save(event);
                LOGGER.info("Mark event with id: {} as archieved", event.getId());
            }
        }
    }
}
