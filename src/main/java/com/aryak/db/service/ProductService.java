package com.aryak.db.service;

import com.aryak.db.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getById(int id);

    Product addProduct(Product product);
}
