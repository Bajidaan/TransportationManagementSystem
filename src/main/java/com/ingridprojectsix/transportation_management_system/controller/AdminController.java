package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.AdminDto;
import com.ingridprojectsix.transportation_management_system.dto.AdminUpdate;
import com.ingridprojectsix.transportation_management_system.model.Admin;
import com.ingridprojectsix.transportation_management_system.model.Users;
import com.ingridprojectsix.transportation_management_system.service.AdminService;
import com.ingridprojectsix.transportation_management_system.utils.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Map<String, String> addAdmin(@RequestBody AdminDto adminDto) {
        return adminService.addAmin(adminDto);
    }

    @GetMapping("/getAll")
    public List<Admin> getAllAdmin() {
        return adminService.getAllAdmin();
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable long id) {
        return adminService.getAdminById(id);
    }

    @PutMapping("/update/{id}")
    public Map<String, String> updateAdmin(@PathVariable long id, @PathVariable AdminUpdate adminUpdate) {
        return adminService.updateAdmin(id, adminUpdate);
    }

}
