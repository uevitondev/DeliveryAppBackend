package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.CartItemDTO;
import com.uevitondev.deliveryapp.dto.OrderDTO;
import com.uevitondev.deliveryapp.dto.ShoppingCartDTO;
import com.uevitondev.deliveryapp.enums.OrderStatus;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.*;
import com.uevitondev.deliveryapp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        StoreRepository storeRepository, AddressRepository addressRepository,
                        ProductRepository productRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.storeRepository = storeRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new OrderDTO(order);
    }


    @Transactional
    public OrderDTO saveNewOrder(ShoppingCartDTO dto) {
        try {
            var user = (PhysicalUser) userDetailsServiceImpl.getUserAuthenticated();
            Store store = storeRepository.getReferenceById(dto.getPizzeriaId());
            Address address = addressRepository.getReferenceById(dto.getAddressId());
            Order order = new Order(null, OrderStatus.PENDENTE, LocalDateTime.now());
            order.setUser(user);
            order.setStore(store);
            order.setAddress(address);
            order = orderRepository.save(order);
            saveOrderItemByOrder(order, dto.getCartItems());
            order.calculateOrderTotal();
            order = orderRepository.save(order);
            return new OrderDTO(order);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity constraint violation");
        }
    }

    public void saveOrderItemByOrder(Order order, Set<CartItemDTO> cartItems) {
        for (CartItemDTO cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(productRepository.findById(cartItem.getProductId())
                    .orElseThrow(ResourceNotFoundException::new));
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setObservation(cartItem.getObservation());
            orderItem.setOrder(order);
            orderItem.calculateOrderItemTotal();
            orderItem = orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }
    }
}