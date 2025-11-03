package com.example.disciplines;

import com.example.professors.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DisciplinesRepository implements PanacheRepositoryBase<DisciplinesSchema, UUID> {
    @PersistenceContext
    private EntityManager em;

//    public List<DisciplinesSchema> findAll() {
//        return this.em.createQuery("SELECT d FROM DisciplinesSchema d", DisciplinesSchema.class).getResultList();
//    }

    public DisciplinesSchema findById(UUID id) {
        return this.em.find(DisciplinesSchema.class, id);
    }

    @Transactional
    public DisciplinesSchema create(DisciplinesSchema entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public DisciplinesSchema update(DisciplinesSchema entity) {
        return em.merge(entity);
    }

    @Transactional
    public boolean delete(UUID id) {
        DisciplinesSchema deleted = em.find(DisciplinesSchema.class, id);
        if(deleted != null) {
            em.remove(deleted);
            return true;
        }
        return false;
    }

     public List<Professor> getProfessorsByDisciplineId(UUID disciplineId) {
        DisciplinesSchema discipline = this.findById(disciplineId);
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
