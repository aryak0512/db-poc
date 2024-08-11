package com.aryak.db.repository.impl;

import com.aryak.db.domain.Product;
import com.aryak.db.repository.ProductRepository;
import com.aryak.db.rowmappers.ProductRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> findAll(JdbcTemplate template) {
        return template.query("select * from products", new ProductRowMapper());
    }

    @Override
    public Product findById(JdbcTemplate template, int id) {
        return template.queryForObject("select * from products where id=?", new ProductRowMapper(), id);
    }

    @Override
    public Product save(JdbcTemplate template, Product product) {

        String sql = """
                INSERT into products(id, name, price) 
                VALUES (?,?,?);
                 """;
        int rowsAffected = template.update(sql, product.id(), product.name(), product.price());

        log.info("Number of rows affected : {}", rowsAffected);
        return product;
    }
}
