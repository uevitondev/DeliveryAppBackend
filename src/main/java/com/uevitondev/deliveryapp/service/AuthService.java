package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.ResponseLoginDTO;
import com.uevitondev.deliveryapp.dto.UserRegistrationDTO;
import com.uevitondev.deliveryapp.enums.TypeUser;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.ConfirmationToken;
import com.uevitondev.deliveryapp.model.PhysicalUser;
import com.uevitondev.deliveryapp.model.User;
import com.uevitondev.deliveryapp.repository.RoleRepository;
import com.uevitondev.deliveryapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public AuthService(
            UserRepository userRepository, RoleRepository roleRepository,
            JwtService jwtService, PasswordEncoder passwordEncoder,
            ConfirmationTokenService confirmationTokenService, EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    @Transactional
    public ResponseLoginDTO signup(UserRegistrationDTO dto) {
        try {
            var role = roleRepository.findByName("ROLE_CLIENT").orElseThrow(ResourceNotFoundException::new);
            PhysicalUser user = new PhysicalUser();
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setTypeUser(TypeUser.PHYSICAL);
            user.setEnabled(false);
            user.getRoles().add(role);
            var userSave = userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(userSave);
            confirmationToken = confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:8080/auth/signup/confirmation?token=" + confirmationToken.getToken();
            String emailContent = emailService.defaultTemplateAccountConfirmation(link, user.getUsername());
            emailService.sendEmailTokenRegistration(user.getEmail(), emailContent);

            return new ResponseLoginDTO(confirmationToken.getToken());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Usuário já cadastrado!");
        }
    }


    @Transactional
    public String signupConfirmation(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token).orElseThrow(ResourceNotFoundException::new);
        if (confirmationToken.getConfirmedAt() != null) {
            return "Email already confirmed!";
        }
        if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            return "Token Expired!";
        }
        confirmationTokenService.updateConfirmedAt(confirmationToken.getId());
        User user = userRepository.getReferenceById(confirmationToken.getUser().getId());
        user.setEnabled(true);
        userRepository.save(user);

        return "sua conta foi ativada com sucesso!";
    }

    public ResponseLoginDTO signin(Authentication authentication) {
        return new ResponseLoginDTO(authenticate(authentication));
    }


    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }


}