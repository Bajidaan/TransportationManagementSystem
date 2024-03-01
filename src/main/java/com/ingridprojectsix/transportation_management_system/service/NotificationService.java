package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.exception.UsersNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.Notification;
import com.ingridprojectsix.transportation_management_system.model.domain.NotificationStatus;
import com.ingridprojectsix.transportation_management_system.dto.NotificationUpdateRequest;
import com.ingridprojectsix.transportation_management_system.model.Users;
import com.ingridprojectsix.transportation_management_system.repository.NotificationRepository;
import com.ingridprojectsix.transportation_management_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendNotification(Long userId, String message) {
        Users user = userRepository.findById(userId)
                .orElseThrow(UsersNotFoundException::new);;

        if (user != null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setNotificationTime(LocalDateTime.now());
            notification.setRead(false);
            notification.setStatus(NotificationStatus.PENDING);

            notificationRepository.save(notification);
        }
    }

    public List<Notification> getAllNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Transactional
    public void updateNotificationStatus(Long notificationId, Boolean isRead, NotificationStatus newStatus) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setStatus(newStatus);
            //notificationRepository.save(notification);

            if (isRead != null) {
                notification.setRead(isRead);
            }
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void updateNotification(Long notificationId, NotificationUpdateRequest updateRequest) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();

            if (updateRequest.getMessage() != null) {
                notification.setMessage(updateRequest.getMessage());
            }

            if (updateRequest.getIsRead() != null) {
                notification.setRead(updateRequest.getIsRead());
            }

            if (updateRequest.getIsAccepted() != null) {
                // Assuming isAccepted is now represented by the status field
                notification.setStatus(updateRequest.getIsAccepted() ? NotificationStatus.ACCEPTED : NotificationStatus.REJECTED);
            }

            notificationRepository.save(notification);
        }
    }
}
