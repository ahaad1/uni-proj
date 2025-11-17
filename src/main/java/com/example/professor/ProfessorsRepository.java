package com.example.professor;

import com.example.common.models.PageResult;
import com.example.discipline.DisciplinesSchema;
import com.example.discipline.dtos.DisciplinesDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProfessorsRepository implements PanacheRepositoryBase<Professor, UUID> {

    public PageResult<Professor> findAllPaged(int page, int size) {
        var query = findAll();
        long total = query.count();
        List<Professor> content = query.page(Page.of(page, size)).list();
        return new PageResult<>(content, total, page, size);
    }

    public Professor getOne(UUID id) {
        return findById(id);
    }

    @Transactional
    public Professor create(Professor professor) {
        persist(professor);
        return professor;
    }

    @Transactional
    public Professor update(Professor professor) {
        return getEntityManager().merge(professor);
    }

    @Transactional
    public boolean delete(UUID id) {
        return deleteById(id);
    }

    public DisciplinesSchema getDisciplineForProfessor(UUID professorId) {
        Professor professor = findById(professorId);
        return professor != null ? professor.getDiscipline() : null;
    }
}
