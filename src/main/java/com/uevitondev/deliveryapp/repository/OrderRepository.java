package com.uevitondev.deliveryapp.repository;

import com.uevitondev.deliveryapp.model.Order;
import com.uevitondev.deliveryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
