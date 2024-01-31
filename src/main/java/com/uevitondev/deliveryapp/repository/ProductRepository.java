package com.uevitondev.deliveryapp.repository;

import com.uevitondev.deliveryapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category  JOIN FETCH p.store")
    Page<Product> findAllProductsPaged(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN FETCH p.category  JOIN FETCH p.store where p.store.id = :id")
    Page<Product> findAllProductsPagedByStoreId(Long id, Pageable pageable);

}
