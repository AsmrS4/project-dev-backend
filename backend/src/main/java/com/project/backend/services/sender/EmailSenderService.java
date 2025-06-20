package com.project.backend.services.sender;

import com.project.backend.dto.event.EventUpdateDto;
import com.project.backend.entities.event.Event;
import jakarta.mail.MessagingException;

public interface EmailSenderService {
    void sendEventChangesNotification(Event event) throws MessagingException;
    void sendEventDateChangeNotification(Event event, EventUpdateDto dto) throws MessagingException;
    void sendNewEventNotification(Event event) throws MessagingException;
}
