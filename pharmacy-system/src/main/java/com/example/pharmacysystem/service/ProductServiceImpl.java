package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Optional<Product> getProductBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(String serialNumber, Product updatedProduct) {
        return null;
    }


    @Override
    public void deleteProduct(String serialNumber) {

    }
}