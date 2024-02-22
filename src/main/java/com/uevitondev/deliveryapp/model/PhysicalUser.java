package com.uevitondev.deliveryapp.model;

import com.uevitondev.deliveryapp.enums.TypeUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_physicaluser")
@PrimaryKeyJoinColumn(name = "user_id")
public class PhysicalUser extends User {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String cpf;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<Order> orders = new HashSet<>();

    public PhysicalUser() {
    }

    public PhysicalUser(Long id, String username, String email, String password, TypeUser typeUser, LocalDateTime createdAt) {
        super(id, username, email, password, typeUser, createdAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
