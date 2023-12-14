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


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductBySerialNumber(String serialNumber) {
        return productRepository.findBySerialNumber(serialNumber);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String serialNumber, Product updatedProduct) {
        // Check if the product with the given serial number exists
        Optional<Product> existingProductOptional = getProductBySerialNumber(serialNumber);

        if (existingProductOptional.isPresent()) {
            // Get the existing product
            Product existingProduct = existingProductOptional.get();

            // Update the existing product using setters
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setProductionDate(updatedProduct.getProductionDate());
            existingProduct.setExpiryDate(updatedProduct.getExpiryDate());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPhoto(updatedProduct.getPhoto());

            // Save the updated product
            return productRepository.save(existingProduct);
        } else {
            return null; // Handle the case where the product with the given serial number doesn't exist
        }
    }


    @Override
    public void deleteProduct(String serialNumber) {
        productRepository.deleteById(serialNumber);//Given that the Id is the serial number given
    }


}
