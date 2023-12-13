package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    List<Product> getAllAvailableProducts();

    boolean isAvailable(String SN);
}
