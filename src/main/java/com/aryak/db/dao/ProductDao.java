package com.aryak.db.dao;

import com.aryak.db.domain.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    void save(Product p);

    void sleep();
}
