package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.AccountResponse;
import com.ingridprojectsix.transportation_management_system.dto.ForgetPasswordRequest;
import com.ingridprojectsix.transportation_management_system.dto.LoginRequest;
import com.ingridprojectsix.transportation_management_system.dto.SignupRequest;

public interface UserService {
    AccountResponse login(LoginRequest request);

    AccountResponse register(SignupRequest request);
}
