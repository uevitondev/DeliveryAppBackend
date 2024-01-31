package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.model.Role;
import com.uevitondev.deliveryapp.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserRequestDTO implements Serializable {

    private Long id;

    @NotBlank(message = "email: is mandatory")
    @Email(message = "email: is invalid")
    private String email;

    @NotBlank(message = "password: is mandatory")
    private String password;

    @NotEmpty(message = "rolesId: is empty")
    private final Set<Long> rolesId = new HashSet<>();

    public UserRequestDTO() {
    }

    public UserRequestDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserRequestDTO(User user, Set<Role> roles) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        roles.forEach(role -> this.rolesId.add(role.getId()));
    }

    public UserRequestDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Long> getRolesId() {
        return rolesId;
    }
}
