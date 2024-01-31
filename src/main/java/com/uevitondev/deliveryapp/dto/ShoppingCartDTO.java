package com.uevitondev.deliveryapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCartDTO implements Serializable {

    @NotNull(message = "pizzeriaId: is mandatory")
    private Long pizzeriaId;

    @NotEmpty(message = "cartItems: cannot be empty")
    private final Set<@Valid OrderItemCartDTO> cartItems = new HashSet<>();

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(Long pizzeriaId) {
        this.pizzeriaId = pizzeriaId;
    }

    public Long getPizzeriaId() {
        return pizzeriaId;
    }

    public void setPizzeriaId(Long pizzeriaId) {
        this.pizzeriaId = pizzeriaId;
    }

    public Set<OrderItemCartDTO> getCartItems() {
        return cartItems;
    }
}
