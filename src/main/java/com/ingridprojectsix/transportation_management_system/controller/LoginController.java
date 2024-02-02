package com.ingridprojectsix.transportation_management_system.controller;

import com.ingridprojectsix.transportation_management_system.dto.AccountResponse;
import com.ingridprojectsix.transportation_management_system.dto.LoginRequest;
import com.ingridprojectsix.transportation_management_system.dto.SignupRequest;
import com.ingridprojectsix.transportation_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AccountResponse login(@RequestBody LoginRequest request){
      return  userService.login(request);
    }

    @PostMapping("/register")
    public AccountResponse register(@RequestBody SignupRequest request){
        return userService.register(request);
    }
}
