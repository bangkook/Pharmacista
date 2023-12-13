package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllAvailableProducts() {
        try {
            return productRepository.findAllAvailableProducts();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving available products", e);
        }
    }


    @Override
    public boolean isAvailable(String SN) {
        try {
            Optional<Product> p = productRepository.findById(SN);
            return p.map(product -> product.getQuantity() > 0).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking product availability", e);
        }
    }
}
