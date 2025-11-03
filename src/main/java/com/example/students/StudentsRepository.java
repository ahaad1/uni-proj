package com.example.students;

import com.example.professors.Professor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StudentsRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Student> findAll() {
        return entityManager.createQuery("select s from Student s", Student.class).getResultList();
    }

    public Student findById(UUID id){
        return entityManager.find(Student.class, id);
    }

    @Transactional
    public Student create(Student student) {
        entityManager.persist(student);
        return student;
    }

    @Transactional
    public Student update(Student student){
        return entityManager.merge(student);
    }

    @Transactional
    public boolean delete(UUID id){
        Student student = entityManager.find(Student.class, id);
        if(student == null) return false;
        entityManager.remove(student);
        return true;
    }

    public List<Professor> getProfessorsByStudentId(UUID studentId) {
        Student student = this.findById(studentId);
        if(student == null) return Collections.emptyList();
        return student.getProfessors();
    }
}
