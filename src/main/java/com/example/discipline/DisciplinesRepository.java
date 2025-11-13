package com.example.discipline;

import com.example.common.models.PageResult;
import com.example.professor.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DisciplinesRepository implements PanacheRepositoryBase<DisciplinesSchema, UUID> {

    public PageResult<DisciplinesSchema> findAllPaged(int page, int size) {
        var query = findAll();
        long total = query.count();
        List<DisciplinesSchema> content = query.page(Page.of(page, size)).list();
        return new PageResult<>(content, total, page, size);
    }

    @Transactional
    public DisciplinesSchema create(DisciplinesSchema entity) {
        persist(entity);
        return entity;
    }

    @Transactional
    public DisciplinesSchema update(DisciplinesSchema entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional
    public boolean delete(UUID id) {
        return deleteById(id);
    }

    public DisciplinesSchema findOne(UUID id) {
        return findById(id);
    }

     public List<Professor> getProfessorsByDisciplineId(UUID disciplineId) {
        DisciplinesSchema discipline = findById(disciplineId);
        if(discipline != null) {
            return discipline.getProfessors();
        }
        return Collections.emptyList();
     }



     public DisciplinesSchema getDisciplineByPofessor(UUID professorId) {
        return find("SELECT d FROM DisciplinesSchema d JOIN d.professors p WHERE p.id = :professorId",
                Parameters.with("professorId", professorId)).firstResult();
     }
}
