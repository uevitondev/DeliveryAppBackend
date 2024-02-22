package com.uevitondev.deliveryapp.controller;

import com.uevitondev.deliveryapp.dto.AddressDTO;
import com.uevitondev.deliveryapp.dto.OrderDTO;
import com.uevitondev.deliveryapp.dto.UserRequestDTO;
import com.uevitondev.deliveryapp.dto.UserResponseDTO;
import com.uevitondev.deliveryapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> insertNewUser(@RequestBody @Valid UserRequestDTO dto) {
        var userResponseDTO = userService.insertNewUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        var userResponseDTO = userService.updateUserById(id, dto);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        return ResponseEntity.ok().body(userService.findUserOrders());
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        return ResponseEntity.ok().body(userService.findUserAddresses());
    }


}
