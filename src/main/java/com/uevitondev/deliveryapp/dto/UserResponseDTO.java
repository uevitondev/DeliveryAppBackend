package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.enums.TypeUser;
import com.uevitondev.deliveryapp.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserResponseDTO implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String password;
    private TypeUser typeUser;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private boolean isEnabled;
    private final Set<RoleDTO> roles = new HashSet<>();


    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.typeUser = user.getTypeUser();
        this.createdAt = user.getCreatedAt();
        this.updateAt = user.getUpdateAt();
        this.isEnabled = user.isEnabled();
        user.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }
}