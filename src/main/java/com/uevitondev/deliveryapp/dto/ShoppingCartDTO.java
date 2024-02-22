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
    @NotNull(message = "addressId: is mandatory")
    private Long addressId;

    @NotEmpty(message = "cartItems: cannot be empty")
    private final Set<@Valid CartItemDTO> cartItems = new HashSet<>();

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(Long pizzeriaId, Long addressId) {
        this.pizzeriaId = pizzeriaId;
        this.addressId = addressId;
    }

    public Long getPizzeriaId() {
        return pizzeriaId;
    }

    public void setPizzeriaId(Long pizzeriaId) {
        this.pizzeriaId = pizzeriaId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Set<CartItemDTO> getCartItems() {
        return cartItems;
    }
}
