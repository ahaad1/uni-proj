package com.example.professors;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import com.example.disciplines.DisciplinesRepository;
import com.example.disciplines.dtos.DisciplinesDTO;
import com.example.disciplines.exceptions.DisciplineNotFound;
import com.example.disciplines.mappers.DisciplinesMapper;
import com.example.professors.dtos.CreateProfessorDTO;
import com.example.professors.dtos.ProfessorsDTO;
import com.example.professors.dtos.UpdateProfessorDto;
import com.example.professors.exceptions.ProfessorNotFound;
import com.example.professors.mappers.ProfessorsMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProfessorsService {
    @Inject
    ProfessorsRepository professorsRepository;

    @Inject
    DisciplinesRepository disciplinesRepository;

    @Inject
    ProfessorsMapper professorsMapper;

    @Inject
    DisciplinesMapper disciplinesMapper;

    public ResponseData<List<ProfessorsDTO>> getAll() {
        List<ProfessorsDTO> professors = professorsMapper.toProfessorsDto(this.professorsRepository.findAll());
        if(professors.isEmpty()) {
            throw new ProfessorNotFound("No professors found");
        }
        return new ResponseData<>(professors, ResponseStatus.SUCCESS, "Professors retrieved successfully");
    }

    public ResponseData<ProfessorsDTO> getOne(UUID id) {
        Professor professor = this.professorsRepository.findById(id);
        if(professor == null) {
            throw new ProfessorNotFound("Professor with id " + id + " not found");
        }
        return new ResponseData<>(this.professorsMapper.toDto(professor), ResponseStatus.SUCCESS, "Professors retrieved successfully");
    }

    @Transactional
    public ResponseData<ProfessorsDTO> create(CreateProfessorDTO professorsDTO) {
        Professor professor = this.professorsMapper.toEntity(professorsDTO);
        this.professorsRepository.create(professor);
        return new ResponseData<>(this.professorsMapper.toDto(professor), ResponseStatus.SUCCESS, "Professors created successfully");
    }

    @Transactional
    public ResponseData<ProfessorsDTO> update(UUID id, UpdateProfessorDto professorsDTO) {
        Professor existing = this.professorsRepository.findById(id);
        if(existing == null) {
            throw new ProfessorNotFound("Professor with id " + id + " not found");
        }

        this.professorsMapper.updateProfessorFromDto(professorsDTO, existing);
        Professor professor = this.professorsRepository.update(existing);
        return new ResponseData<>(professorsMapper.toDto(professor), ResponseStatus.SUCCESS, "Professor updated successfully");
    }

    @Transactional
    public ResponseData<Void> delete(UUID id) {
        if(!this.professorsRepository.delete(id)) {
            throw new ProfessorNotFound("Professor with id " + id + " not found");
        }
        return new ResponseData<>(null, ResponseStatus.SUCCESS, "Professors deleted successfully");
    }

    public ResponseData<DisciplinesDTO> getDisciplineByProfessorId(UUID professorId) {
        if(professorsRepository.findById(professorId) == null) {
            throw new ProfessorNotFound("Professor with id " + professorId + " not found");
        }
        DisciplinesDTO discipline = disciplinesMapper.toDto(disciplinesRepository.getDisciplineByPofessor(professorId));
        if(discipline == null) {
            throw new DisciplineNotFound("No Discipline found for professor id " + professorId);
        }
        return new ResponseData<>(discipline, ResponseStatus.SUCCESS, "Professor's disciplines retrieved successfully");
    }
}
