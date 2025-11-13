package com.example.professor;

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

    public List<Professor> findAllPaged(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }

    @Transactional
    public Professor create(Professor professor){
        persist(professor);
        return professor;
    }

    @Transactional
    public Professor update(Professor professor) {
        return  getEntityManager().merge(professor);
    }

    @Transactional
    public boolean delete(UUID id) {
        return deleteById(id);
    }

    public DisciplinesDTO getDisciplinesByProfessorId(UUID professorId) {
        Professor professor = findById(professorId);
        if(professor == null) return null;
        return new DisciplinesDTO(professor.getDiscipline());
    }
}
