package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.repository.RoleRepository;
import com.uevitondev.deliveryapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String signin(Authentication authentication) {
        return authenticate(authentication);
    }


    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }


}
