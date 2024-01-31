package com.uevitondev.deliveryapp.model;

import com.uevitondev.deliveryapp.enums.TypeUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_legaluser")
@PrimaryKeyJoinColumn(name = "user_id")
public class LegalUser extends User {
    @Column(nullable = false)
    private String fantasyName;

    @Column(nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<Address> address = new HashSet<>();

    public LegalUser() {
    }

    public LegalUser(Long id, String username, String email, String password, TypeUser typeUser, LocalDateTime createdAt) {
        super(id, username, email, password, typeUser, createdAt);
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Set<Address> getAddress() {
        return address;
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
