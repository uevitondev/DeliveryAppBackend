package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.model.Role;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class RoleDTO implements Serializable {
    private Long id;
    @NotBlank(message = "name: is mandatory")
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
