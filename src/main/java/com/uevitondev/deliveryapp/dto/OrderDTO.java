package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.enums.OrderStatus;
import com.uevitondev.deliveryapp.model.Order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private OrderStatus status;
    private Double total;
    private Long userId;
    private Long storeId;
    private final Set<OrderItemDTO> orderItems = new HashSet<>();

    public OrderDTO() {
    }

    public OrderDTO(Long id, LocalDateTime createdAt, LocalDateTime updateAt, OrderStatus status, Double total, Long userId, Long storeId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.status = status;
        this.total = total;
        this.userId = userId;
        this.storeId = storeId;
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.createdAt = order.getCreatedAt();
        this.updateAt = order.getUpdateAt();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        order.getOrderItems().forEach(orderItem -> this.orderItems.add(new OrderItemDTO(orderItem)));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }
}
