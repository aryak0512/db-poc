package com.aryak.db.dao;

import com.aryak.db.domain.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void save(Product p);

    Product findById(int id);

    int deleteById(int id);

    int [] saveAll(List<Product> products);

    // use this method when the size of list is too large
    int [][] save(List<Product> products);
}
