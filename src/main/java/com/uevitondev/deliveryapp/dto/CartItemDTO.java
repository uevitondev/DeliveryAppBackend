package com.uevitondev.deliveryapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
    @NotNull(message = "productId: is mandatory")
    private Long productId;
    @NotNull(message = "quantity: is mandatory")
    @Min(value = 1, message = "qtd: minimum value = 1")
    private Integer quantity;
    private String observation;

    public CartItemDTO() {
    }

    public CartItemDTO(Long productId, Integer quantity, String observation) {
        this.productId = productId;
        this.quantity = quantity;
        this.observation = observation;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}

