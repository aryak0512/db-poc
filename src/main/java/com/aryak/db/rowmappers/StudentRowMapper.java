package com.aryak.db.rowmappers;

import com.aryak.db.domain.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        var name = rs.getString("name");
        var id = rs.getLong("id");
        var address = rs.getString("address");
        return new Student(id, name, address);
    }
}
