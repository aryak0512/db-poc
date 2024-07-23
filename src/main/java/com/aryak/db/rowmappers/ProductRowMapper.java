package com.aryak.db.rowmappers;

import com.aryak.db.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
