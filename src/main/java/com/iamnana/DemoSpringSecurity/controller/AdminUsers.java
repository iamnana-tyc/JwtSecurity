package com.iamnana.DemoSpringSecurity.controller;

import com.iamnana.DemoSpringSecurity.entity.Product;
import com.iamnana.DemoSpringSecurity.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminUsers {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/public/product")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("/admin/saveproduct")
    public ResponseEntity<Product> saveProduct(@RequestBody Product productRequest){
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        return ResponseEntity.ok(productRepository.save(productToSave));
    }

    @GetMapping("/user/alone")
    public ResponseEntity<String> userAlone(){
        return ResponseEntity.ok("USERS ALONE CAN ACCESS THIS API");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<String> bothAdminAndUser(){
        return ResponseEntity.ok("BOTH USERS AND ADMIN CAN ACCESS THIS API");
    }
}
