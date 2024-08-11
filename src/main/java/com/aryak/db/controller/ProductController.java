package com.aryak.db.controller;

import com.aryak.db.domain.Product;
import com.aryak.db.multitenancy.TenantContext;
import com.aryak.db.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{clientId}")
    public ResponseEntity<List<Product>> getAll(@PathVariable String clientId) {
        TenantContext.setCurrentTenant(clientId);
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{clientId}/id/{id}")
    public ResponseEntity<Product> getById(@PathVariable String clientId, @PathVariable int id) {
        TenantContext.setCurrentTenant(clientId);
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/{clientId}")
    public ResponseEntity<Product> addProduct(@PathVariable String clientId, @RequestBody Product product) {
        TenantContext.setCurrentTenant(clientId);
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);
    }

}
