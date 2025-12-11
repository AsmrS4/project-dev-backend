package com.project.backend.services.sender;

import com.project.backend.dto.event.EventUpdateDto;
import com.project.backend.entities.event.Event;
import com.project.backend.repositories.BookingRepository;
import com.project.backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    @Value("${spring.mail.username}")
    private String emailFrom;

    private Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    @Override
    @Async
    public void sendEventChangesNotification(Event event) throws MessagingException {

        List<String> emails = bookingRepository.getSubscribersEmails(event.getId());
        LOGGER.info("Subscribers email {}", emails);
        String text = "К сожалению, мероприятие "
                + "\"" + event.getTitle() + "\""
                + " пришлось отменить по определенным причинам. Приносим свои извинения.";

        emails.parallelStream().forEach(address -> {
            try {
                LOGGER.info("sent to {}", address);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setSubject("Fresh Nights");
                helper.setText(text, true);
                helper.setTo(address.toString());
                helper.setFrom(emailFrom);

                sendEmail(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        LOGGER.info("All addresses received message ");
    }

    @Override
    @Async
    public void sendEventDateChangeNotification(Event event, EventUpdateDto dto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        List<String> emails = bookingRepository.getSubscribersEmails(event.getId());
        LOGGER.info("Subscribers email {}", emails);
        String text = "Мероприятие "
                + "\"" + event.getTitle() + "\""
                + " переносится на новую дату " + dto.getDateTime() ;
        emails.parallelStream().forEach(address -> {
            try {
                helper.setSubject("Fresh Nights");
                helper.setText(text, true);
                helper.setTo(address);
                helper.setFrom(emailFrom);

                sendEmail(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Async
    public void sendNewEventNotification(Event event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        List<String> emails = userRepository.getUsersEmail();
        String text = "Дорогие друзья! Сообщаем вам о проведении мероприятия "
                + "\"" + event.getTitle() + "\""
                + " которое пройдет " + event.getDateTime() + ". Надеемся, что вы его обязательно посетите.";
        emails.parallelStream().forEach(address -> {
            try {
                helper.setSubject("Fresh Nights");
                helper.setText(text, true);
                helper.setTo(address);
                helper.setFrom(emailFrom);

                sendEmail(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Async
    private void sendEmail(MimeMessage message) throws MessagingException {
        try {
            mailSender.send(message);
            LOGGER.info("Email sent successfully");
        } catch (MailException e) {
            LOGGER.error("Failed to send email", e);
            throw e;
        }
    }

}
