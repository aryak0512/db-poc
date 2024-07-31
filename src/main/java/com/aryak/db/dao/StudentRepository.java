package com.aryak.db.dao;

import com.aryak.db.domain.Student;

import java.util.List;

public interface StudentRepository {

    Student findById(int id);

    List<Student> findAll();

}
