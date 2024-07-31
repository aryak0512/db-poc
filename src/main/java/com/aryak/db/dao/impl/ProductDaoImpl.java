package com.aryak.db.dao.impl;

import com.aryak.db.dao.ProductDao;
import com.aryak.db.domain.Product;
import com.aryak.db.rowmappers.ProductRowMapper;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    public static final String INSERT_QUERY = "insert into products (id, name, price) values (?, ?, ?)";

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", new ProductRowMapper());
    }

    @Override
    public void save(Product p) {
        jdbcTemplate.update(INSERT_QUERY, p.id(), p.name(), p.price());
    }

    @Override
    public Product findById(int id) {
        return jdbcTemplate.queryForObject("select * from products where id=?", new ProductRowMapper(), id);
    }

    public int deleteById(int id){
        return jdbcTemplate.update("delete from products where id = ?", id);
    }

    @Override
    public int[] saveAll(List<Product> products) {

        return jdbcTemplate.batchUpdate(INSERT_QUERY, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, products.get(i).id());
                ps.setString(2, products.get(i).name());
                ps.setDouble(3, products.get(i).price());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        });
    }

    @Override
    public int[][] save(List<Product> products) {

        return jdbcTemplate.batchUpdate(INSERT_QUERY,  products,100, (ps, product) -> {
            ps.setInt(1, product.id());
            ps.setString(2, product.name());
            ps.setDouble(3, product.price());
        });
    }

}
