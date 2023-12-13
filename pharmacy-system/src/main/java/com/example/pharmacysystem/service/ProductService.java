package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductBySerialNumber(String serialNumber);

    Product addProduct(Product product);

    Product updateProduct(String serialNumber, Product updatedProduct);

    void deleteProduct(String serialNumber);
}
