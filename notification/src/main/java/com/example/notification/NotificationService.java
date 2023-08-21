package com.example.notification;

import com.example.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record NotificationService(NotificationRepository repository) {
    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.customerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .message(notificationRequest.message())
                .sender("GiondCode")
                .sentAt(LocalDateTime.now())
                .build();
        repository.save(notification);
    }
}
