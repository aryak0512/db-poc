package com.aryak.db.dao.impl;

import com.aryak.db.dao.ProductDao;
import com.aryak.db.domain.Product;
import com.aryak.db.rowmappers.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", new ProductRowMapper());
    }

    @Override
    public void save(Product p) {
        jdbcTemplate.update("insert into products (id, name, price) values (?, ?, ?)", p.id(), p.name(), p.price());
    }

    @Override
    public void sleep() {
        jdbcTemplate.execute("SELECT SLEEP(1)");
    }

    // CREATE ALIAS CUSTOM_SLEEP FOR "com.aryak.db.beans.CustomFunction.sleep";CALL CUSTOM_SLEEP(5000); select * from products;

    public void delayed(){
        jdbcTemplate.execute("CREATE ALIAS CUSTOM_SLEEP FOR \"com.aryak.db.beans.CustomFunction.sleep\";CALL CUSTOM_SLEEP(5000); select * from products");
    }
}
