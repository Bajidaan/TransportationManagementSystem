package com.ingridprojectsix.transportation_management_system.repository;

import com.ingridprojectsix.transportation_management_system.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
}