package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")//add routing to /inventory
public class InventoryController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return null;
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        return null;
    }

    @PutMapping("/products/{serialNumber}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable String serialNumber,
            @RequestBody Product updatedProduct
    ) {
        return null;
    }

    // Delete a product by serial number
    @DeleteMapping("/products/{serialNumber}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String serialNumber) {
       return null;
    }

}
