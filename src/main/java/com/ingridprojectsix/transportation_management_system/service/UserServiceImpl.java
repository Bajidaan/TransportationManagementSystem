package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.config.JwtTokenProvider;
import com.ingridprojectsix.transportation_management_system.dto.AccountResponse;
import com.ingridprojectsix.transportation_management_system.dto.ForgetPasswordRequest;
import com.ingridprojectsix.transportation_management_system.dto.LoginRequest;
import com.ingridprojectsix.transportation_management_system.dto.SignupRequest;
import com.ingridprojectsix.transportation_management_system.model.Users;
import com.ingridprojectsix.transportation_management_system.repository.UserRepository;
import com.ingridprojectsix.transportation_management_system.utils.SignupUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AccountResponse login(LoginRequest request) {
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return AccountResponse.builder()
                .responseCode("200")
                .responseMessage(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    public AccountResponse register(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            return AccountResponse.builder()
                    .responseCode(SignupUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(SignupUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        Users users = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Users saveUser = userRepository.save(users);
        return AccountResponse.builder()
                .responseCode(SignupUtils.SUCCESS_CODE)
                .responseMessage(SignupUtils.SUCCESS_MESSAGE)
                .build();
    }

}
