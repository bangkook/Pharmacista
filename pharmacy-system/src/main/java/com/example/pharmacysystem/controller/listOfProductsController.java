package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = {"/product"})
@RestController
@CrossOrigin()
public class listOfProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping("/getAllAvailableProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllAvailableProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/isAvailableProducts/{SN}")
    public ResponseEntity<Boolean> isAvailableProducts(@PathVariable String SN) {
        try {
            boolean isAvailable = productService.isAvailable(SN);
            return new ResponseEntity<>(isAvailable, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
