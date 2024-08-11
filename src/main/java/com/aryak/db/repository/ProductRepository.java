package com.aryak.db.repository;

import com.aryak.db.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll(JdbcTemplate template);

    Product findById(JdbcTemplate template, int id);

    Product save(JdbcTemplate template, Product product);
}
