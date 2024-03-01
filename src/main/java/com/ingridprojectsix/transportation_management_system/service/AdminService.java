package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.AdminDto;
import com.ingridprojectsix.transportation_management_system.dto.AdminUpdate;
import com.ingridprojectsix.transportation_management_system.exception.AdminNotFoundException;
import com.ingridprojectsix.transportation_management_system.exception.UsersNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.Admin;
import com.ingridprojectsix.transportation_management_system.model.Users;
import com.ingridprojectsix.transportation_management_system.repository.AdminRepository;
import com.ingridprojectsix.transportation_management_system.repository.UserRepository;
import com.ingridprojectsix.transportation_management_system.utils.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> addAmin(AdminDto adminDto) {
        adminRepository.save(convertToAdmin(adminDto));

        return MessageResponse.displayMessage("Successfully added an admin");
    }

    private Admin convertToAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        Users user = userRepository.findById(adminDto.getUserId())
                .orElseThrow(UsersNotFoundException::new);

        admin.setFirstName(user.getFirstName());
        admin.setLastName(user.getLastName());
        admin.setEmail(user.getEmail());
        admin.setPhoneNumber(adminDto.getPhoneNumber());
        admin.setAddress(adminDto.getAddress());
        admin.setUser(user);

        return admin;
    }

    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(long id) {
        return adminRepository.findById(id).orElseThrow(AdminNotFoundException::new);
    }

    public Map<String, String> updateAdmin(long id, AdminUpdate adminUpdate) {
        Admin admin = new Admin();
        Users user = userRepository.findById(id).orElseThrow(UsersNotFoundException::new);

        admin.setUser(user);
        admin.setFirstName(adminUpdate.getFirstName());
        admin.setLastName(adminUpdate.getLastName());
        admin.setAddress(adminUpdate.getAddress());
        admin.setPhoneNumber(adminUpdate.getPhoneNumber());
        admin.setEmail(adminUpdate.getEmail());

        adminRepository.save(admin);

        return MessageResponse.displayMessage("Updated");
    }
}
