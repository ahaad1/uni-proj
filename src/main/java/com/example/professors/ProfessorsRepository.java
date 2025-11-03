package com.example.professors;

import com.example.disciplines.dtos.DisciplinesDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProfessorsRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Professor> findAll() {
        return entityManager.createQuery("select p from Professor p", Professor.class).getResultList();
    }

    public Professor findById(UUID id) {
        return entityManager.find(Professor.class, id);
    }

    @Transactional
    public Professor create(Professor professor){
        entityManager.persist(professor);
        return professor;
    }

    @Transactional
    public Professor update(Professor professor) {
        return  entityManager.merge(professor);
    }

    @Transactional
    public boolean delete(UUID id) {
        Professor professor = entityManager.find(Professor.class, id);
        if(professor == null) return false;
        entityManager.remove(professor);
        return true;
    }

    public DisciplinesDTO getDisciplinesByProfessorId(UUID professorId) {
        Professor professor = findById(professorId);
        if(professor == null) return null;
        return new DisciplinesDTO(professor.getDiscipline());
    }
}
