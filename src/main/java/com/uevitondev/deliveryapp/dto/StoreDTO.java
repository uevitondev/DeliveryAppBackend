package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.model.Store;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class StoreDTO implements Serializable {
    private Long id;
    @NotBlank(message = "name: is mandatory")
    private String name;

    public StoreDTO() {
    }

    public StoreDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StoreDTO(Store pizzeria) {
        this.id = pizzeria.getId();
        this.name = pizzeria.getName();
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
