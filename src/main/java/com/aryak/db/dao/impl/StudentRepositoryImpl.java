package com.aryak.db.dao.impl;

import com.aryak.db.dao.StudentRepository;
import com.aryak.db.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Student findById(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
        Query query = entityManager.createNativeQuery("select * from students");
        TypedQuery<Student> typedQuery = entityManager.createNamedQuery("select s from students s", Student.class);
        return typedQuery.getResultList();
    }
}
