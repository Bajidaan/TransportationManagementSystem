package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);
}
