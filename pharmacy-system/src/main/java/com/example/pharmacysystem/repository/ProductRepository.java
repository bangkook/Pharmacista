package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getProductBySerialNumber(String serialNumbers);

    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findAllAvailableProducts();

}

