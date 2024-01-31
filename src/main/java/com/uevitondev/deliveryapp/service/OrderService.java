package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.OrderDTO;
import com.uevitondev.deliveryapp.dto.OrderItemCartDTO;
import com.uevitondev.deliveryapp.dto.ShoppingCartDTO;
import com.uevitondev.deliveryapp.enums.OrderStatus;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.Order;
import com.uevitondev.deliveryapp.model.OrderItem;
import com.uevitondev.deliveryapp.model.Product;
import com.uevitondev.deliveryapp.model.Store;
import com.uevitondev.deliveryapp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        UserRepository userRepository, StoreRepository storeRepository,
                        ProductRepository productRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrdersByUser() {
        var user = userDetailsServiceImpl.getUserAuthenticated();
        return orderRepository.findByUser(user).stream().map(OrderDTO::new).toList();
    }


    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO saveNewOrder(ShoppingCartDTO dto) {
        var user = userDetailsServiceImpl.getUserAuthenticated();
        logger.info("pedido para user: {}", user.getUsername());
        if (!userRepository.existsById(dto.getPizzeriaId())) {
            throw new ResourceNotFoundException();
        }
        Store store = storeRepository.getReferenceById(dto.getPizzeriaId());

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDENTE);
        order.setStore(store);
        saveOrderItemByOrder(order, dto.getCartItems());
        order.setTotal();
        order = orderRepository.save(order);

        return new OrderDTO(order);
    }

    public void saveOrderItemByOrder(Order order, Set<OrderItemCartDTO> orderItems) {
        for (OrderItemCartDTO orderItemCart : orderItems) {
            try {
                Product product = productRepository.getReferenceById(orderItemCart.getProductId());
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(orderItemCart.getQuantity());
                orderItem.setTotal();
                orderItem.setObservation(orderItemCart.getObservation());
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);
                orderItemRepository.save(orderItem);
            } catch (EntityNotFoundException e) {
                throw new ResourceNotFoundException();
            }
        }
    }
}