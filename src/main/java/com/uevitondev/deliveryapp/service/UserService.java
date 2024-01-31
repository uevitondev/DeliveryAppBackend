package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.UserRequestDTO;
import com.uevitondev.deliveryapp.dto.UserResponseDTO;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.Role;
import com.uevitondev.deliveryapp.model.User;
import com.uevitondev.deliveryapp.repository.RoleRepository;
import com.uevitondev.deliveryapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new UserResponseDTO(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO insertNewUser(UserRequestDTO dto) {
        try {
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());

            for (Long roleId : dto.getRolesId()) {
                Role role = roleRepository.getReferenceById(roleId);
                user.getRoles().add(role);
            }
            user = userRepository.save(user);
            return new UserResponseDTO(user);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.toString());
        }
    }

    @Transactional
    public UserResponseDTO updateUserById(Long id, UserRequestDTO dto) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.getRoles().clear();

            for (Long roleId : dto.getRolesId()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(ResourceNotFoundException::new);
                user.getRoles().add(role);
            }

            user = userRepository.save(user);
            return new UserResponseDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity constraint violation");
        }
    }


}
