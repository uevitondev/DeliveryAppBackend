package com.uevitondev.deliveryapp.repository;

import com.uevitondev.deliveryapp.model.LegalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalUserRepository extends JpaRepository<LegalUser, Long> {
}
