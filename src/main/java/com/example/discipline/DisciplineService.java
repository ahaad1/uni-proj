package com.example.discipline;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.PageResult;
import com.example.common.models.ResponseData;
import com.example.discipline.dtos.CreateDisciplineDTO;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.discipline.dtos.UpdateDisciplineDTO;
import com.example.discipline.exceptions.DisciplineNotFound;
import com.example.discipline.mappers.DisciplinesMapper;
import com.example.professor.Professor;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.professor.mappers.ProfessorsMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DisciplineService {

//    DisciplineService(DisciplinesRepository disciplinesRepository, DisciplinesMapper disciplinesMapper) {}


    @Inject
    DisciplinesRepository disciplinesRepository;

    @Inject
    DisciplinesMapper disciplinesMapper;

    @Inject
    ProfessorsMapper professorsMapper;

    public ResponseData<PageResult<DisciplinesDTO>> getAll(int page, int size){
        var pageResult = disciplinesRepository.findAllPaged(page, size);

        List<DisciplinesDTO> dtos =
                disciplinesMapper.toDisciplinesDto(pageResult.getContent());

        if(dtos.isEmpty()){
            throw new DisciplineNotFound("Disciplines not found");
        }

        PageResult<DisciplinesDTO> dtoPage =
                new PageResult<>(dtos, pageResult.getTotalElements(),
                        pageResult.getCurrentPage(), pageResult.getPageSize());


        return new ResponseData<>(dtoPage, ResponseStatus.SUCCESS);
    }

    public ResponseData<DisciplinesDTO> getOne(UUID id){
        DisciplinesSchema discipline = disciplinesRepository.findOne(id);
        if(discipline == null){
            throw new DisciplineNotFound("Discipline not found with id: " + id);
        }
        return new ResponseData<>(
                disciplinesMapper.toDto(discipline),
                ResponseStatus.SUCCESS,
                "Discipline found with id: " + id
        );
    }

    public ResponseData<DisciplinesDTO> create(CreateDisciplineDTO dto){
        DisciplinesSchema discipline = disciplinesMapper.toDiscipline(dto);
        disciplinesRepository.create(discipline);
        return new ResponseData<>(disciplinesMapper.toDto(discipline), ResponseStatus.SUCCESS, "Discipline created");
    }

    public ResponseData<DisciplinesDTO> update(UUID id, UpdateDisciplineDTO disciplinesDTO){
        DisciplinesSchema discipline = disciplinesRepository.findOne(id);
        if(discipline == null){
            throw new DisciplineNotFound("Discipline not found with id: " + id);
        }
        disciplinesMapper.updateDisciplineFromDto(disciplinesDTO, discipline);
        disciplinesRepository.update(discipline);

        return new ResponseData<>(
                disciplinesMapper.toDto(discipline),
                ResponseStatus.SUCCESS,
                "Discipline updated"
        );
    }

    public ResponseData<Void> delete(UUID id){
        if(!disciplinesRepository.delete(id)){
            throw new DisciplineNotFound("Discipline not found with id " + id);
        }
        return new ResponseData<>(ResponseStatus.SUCCESS, "Discipline deleted");
    }

    public ResponseData<List<ProfessorsDTO>> getProfessorsByDisciplineId(UUID id){
        List<Professor> professors = disciplinesRepository.getProfessorsByDisciplineId(id);
        List<ProfessorsDTO> professorsDTOS = professorsMapper.toProfessorsDto(professors);

        return new ResponseData<>(professorsDTOS, ResponseStatus.SUCCESS,
                "Professors by discipline retrieved successfully");
    }
}
