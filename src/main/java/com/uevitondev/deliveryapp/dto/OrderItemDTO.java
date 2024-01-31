package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.model.OrderItem;

import java.io.Serializable;

public class OrderItemDTO implements Serializable {
    private Long id;
    private Integer quantity;
    private Double total;
    private String observation;
    private ProductDTO product;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long id, Integer quantity, Double total, String observation, ProductDTO product) {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
        this.observation = observation;
        this.product = product;
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.total = orderItem.getTotal();
        this.observation = orderItem.getObservation();
        this.product = new ProductDTO(orderItem.getProduct());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}