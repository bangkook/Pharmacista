package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.model.ProductBuilder;
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
        Product existingProduct = existingProductOptional.get();

        // Use the builder to update the existing product
        Product updatedExistingProduct = new ProductBuilder(existingProduct)
                .buildName(updatedProduct.getName())
                .buildQuantity(updatedProduct.getQuantity())
                .buildPrice(updatedProduct.getPrice())
                .buildProductionDate(updatedProduct.getProductionDate())
                .buildExpiryDate(updatedProduct.getExpiryDate())
                .buildDescription(updatedProduct.getDescription())
                .buildPhoto(updatedProduct.getPhoto())
                .build();

        return productRepository.save(updatedExistingProduct);
    }

    @Override
    public void deleteProduct(String serialNumber) {
        productRepository.deleteById(serialNumber);//Given that the Id is the serial number given
    }
}
