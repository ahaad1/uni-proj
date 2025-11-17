package com.example.professor;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.PageResult;
import com.example.common.models.ResponseData;
import com.example.discipline.DisciplinesRepository;
import com.example.discipline.DisciplinesSchema;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.discipline.exceptions.DisciplineNotFound;
import com.example.discipline.mappers.DisciplinesMapper;
import com.example.professor.dtos.CreateProfessorDTO;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.professor.dtos.UpdateProfessorDto;
import com.example.professor.exceptions.ProfessorNotFound;
import com.example.professor.mappers.ProfessorsMapper;
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

    public ResponseData<PageResult<ProfessorsDTO>> getAllPaged(int page, int size) {
        PageResult<Professor> pageResult = professorsRepository.findAllPaged(page, size);

        List<ProfessorsDTO> dtos = professorsMapper.toProfessorsDto(pageResult.getContent());

        PageResult<ProfessorsDTO> dtoPageResult = new PageResult<>(
                dtos,
                pageResult.getTotalPages(),
                pageResult.getCurrentPage(),
                pageResult.getPageSize()
        );

        if(dtos.isEmpty()) {
            throw new ProfessorNotFound("No professors found");
        }

        return new ResponseData<>(dtoPageResult, ResponseStatus.SUCCESS, "Professors retrieved successfully");
    }

    public ResponseData<ProfessorsDTO> getOne(UUID id) {
        Professor professor = professorsRepository.getOne(id);
        if(professor == null) {
            throw new ProfessorNotFound("Professor with id " + id + " not found");
        }
        return new ResponseData<>(
                this.professorsMapper.toDto(professor),
                ResponseStatus.SUCCESS,
                "Professors retrieved successfully"
        );
    }

    public ResponseData<ProfessorsDTO> create(CreateProfessorDTO professorsDTO) {
        Professor professor = professorsMapper.toEntity(professorsDTO);
        this.professorsRepository.create(professor);
        return new ResponseData<>(
                professorsMapper.toDto(professor),
                ResponseStatus.SUCCESS,
                "Professors created successfully"
        );
    }

    public ResponseData<ProfessorsDTO> update(UUID id, UpdateProfessorDto professorsDTO) {
        Professor existing = professorsRepository.findById(id);
        if(existing == null) {
            throw new ProfessorNotFound("Professor with id " + id + " not found");
        }

        this.professorsMapper.updateProfessorFromDto(professorsDTO, existing);
        Professor professor = professorsRepository.update(existing);
        return new ResponseData<>(
                professorsMapper.toDto(professor),
                ResponseStatus.SUCCESS,
                "Professor updated successfully"
        );
    }

    public ResponseData<Void> delete(UUID id) {
        if(!this.professorsRepository.delete(id)) {
            throw new ProfessorNotFound("Professor with id " + id + " not found");
        }
        return new ResponseData<>(
                null,
                ResponseStatus.SUCCESS,
                "Professors deleted successfully"
        );
    }

    public ResponseData<DisciplinesDTO> getDisciplineByProfessorId(UUID professorId) {
        Professor professor = professorsRepository.getOne(professorId);
        if(professor == null) {
            throw new ProfessorNotFound("Professor with id " + professorId + " not found");
        }

        DisciplinesSchema discipline = professor.getDiscipline();
        if(discipline == null) {
            throw new DisciplineNotFound("No Discipline found for professor id " + professorId);
        }

        DisciplinesDTO dto = disciplinesMapper.toDto(discipline);
        return new ResponseData<>(
                dto,
                ResponseStatus.SUCCESS,
                "Professor's disciplines retrieved successfully"
        );
    }
}
