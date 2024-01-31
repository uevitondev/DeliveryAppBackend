package com.uevitondev.deliveryapp.controller;

import com.uevitondev.deliveryapp.dto.ResponseLoginDTO;
import com.uevitondev.deliveryapp.dto.UserRegistrationDTO;
import com.uevitondev.deliveryapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseLoginDTO> signup(@RequestBody @Valid UserRegistrationDTO dto) {
        return ResponseEntity.ok().body(authService.signup(dto));
    }

    @GetMapping("/signup/confirmation")
    public ResponseEntity<String> signupConfirmation(@RequestParam String token) {
        return ResponseEntity.ok().body(authService.signupConfirmation(token));
    }


    @GetMapping("/signin")
    public ResponseEntity<ResponseLoginDTO> signin(Authentication authentication) {
        return ResponseEntity.ok().body(authService.signin(authentication));
    }


}
