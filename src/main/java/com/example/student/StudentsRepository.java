package com.example.student;

import com.example.professor.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StudentsRepository implements PanacheRepositoryBase<Student, UUID> {

    public List<Student> findAllPaged(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }

    @Transactional
    public Student create(Student student) {
        persist(student);
        return student;
    }

    @Transactional
    public Student update(Student student){
        return getEntityManager().merge(student);
    }

    @Transactional
    public boolean delete(UUID id){
        return deleteById(id);
    }

    public List<Professor> getProfessorsByStudentId(UUID studentId) {
        Student student = findById(studentId);
        if(student == null) return Collections.emptyList();
        return student.getProfessors();
    }
}
