package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.model.Notification;
import com.ingridprojectsix.transportation_management_system.model.NotificationStatus;
import com.ingridprojectsix.transportation_management_system.model.NotificationUpdateRequest;
import com.ingridprojectsix.transportation_management_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam Long userId, @RequestParam String message) {
        notificationService.sendNotification(userId, message);
        return ResponseEntity.ok("Notification sent successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getAllNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateNotificationStatus(
            @RequestParam Long notificationId,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam NotificationStatus newStatus
    ) {
        notificationService.updateNotificationStatus(notificationId, isRead, newStatus);
        return ResponseEntity.ok("Notification status updated successfully");
    }

    @PutMapping("/update/{notificationId}")
    public ResponseEntity<String> updateNotification(
            @PathVariable Long notificationId,
            @RequestBody NotificationUpdateRequest updateRequest
    ) {
        notificationService.updateNotification(notificationId, updateRequest);
        return ResponseEntity.ok("Notification updated successfully");
    }

}
