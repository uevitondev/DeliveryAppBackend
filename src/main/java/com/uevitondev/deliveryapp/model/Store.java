package com.uevitondev.deliveryapp.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_store")
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LegalUser user;
    @OneToMany(mappedBy = "store")
    private final Set<Product> products = new HashSet<>();
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<Order> orders = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_store_address",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Address address;

    public Store() {
    }

    public Store(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    public LegalUser getUser() {
        return user;
    }

    public void setUser(LegalUser user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store pizzeria = (Store) o;
        return Objects.equals(id, pizzeria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
