package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductBySerialNumber(String serialNumber){
        return productRepository.getProductBySerialNumber(serialNumber);
    }

    @Override
    public String updateQuantityBySerialNumber(String productSN, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productSN);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (product.getQuantity() - quantity >= 0) {
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product); // Save the changes to the database
                return "Success";
            } else {
                return "empty";
            }
        } else {
            return "outOfStock";
        }
    }

}
