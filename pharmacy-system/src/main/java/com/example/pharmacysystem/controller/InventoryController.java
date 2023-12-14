package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    private ResponseEntity<Object> validateProduct(Product product) {
        // Validate serial number length
        if (product.getSerialNumber().length() != 18) {
            return new ResponseEntity<>("Serial number must be 18 characters.", HttpStatus.BAD_REQUEST);
        }

        // Validate quantity
        if (product.getQuantity() <= 0) {
            return new ResponseEntity<>("Quantity must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        // Validate price
        if (product.getPrice() <= 0) {
            return new ResponseEntity<>("Price must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        // Validate production and expiry date
        Date productionDate = product.getProductionDate();
        Date expiryDate = product.getExpiryDate();

        if (productionDate != null && expiryDate != null) {
            LocalDate localProductionDate = productionDate.toLocalDate();
            LocalDate localExpiryDate = expiryDate.toLocalDate();

            // Validate localProductionDate and localExpiryDate as needed
            if (localProductionDate.isAfter(localExpiryDate) || localProductionDate.isAfter(LocalDate.now())) {
                return new ResponseEntity<>("Production date must be before expiry date and on or before today.", HttpStatus.BAD_REQUEST);
            }
        }

        return null; // Indicates validation success
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        if (productService.getProductBySerialNumber(product.getSerialNumber()).isPresent()) {
            return new ResponseEntity<>("Serial number must be unique.", HttpStatus.CONFLICT);
        }

        ResponseEntity<Object> validationResponse = validateProduct(product);
        if (validationResponse != null) {
            return validationResponse;
        }

        // If all validations pass, add the product
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products/{serialNumber}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable String serialNumber,
            @RequestBody Product updatedProduct
    ) {
        ResponseEntity<Object> validationResponse = validateProduct(updatedProduct);
        if (validationResponse != null) {
            return validationResponse;
        }

        Product updated = productService.updateProduct(serialNumber, updatedProduct);
        return  new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete a product by serial number
    @DeleteMapping("/products/{serialNumber}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String serialNumber) {
            productService.deleteProduct(serialNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}