package com.example.disciplines;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import com.example.disciplines.dtos.CreateDisciplineDTO;
import com.example.disciplines.dtos.DisciplinesDTO;
import com.example.disciplines.dtos.UpdateDisciplineDTO;
import com.example.disciplines.exceptions.DisciplineNotFound;
import com.example.disciplines.mappers.DisciplinesMapper;
import com.example.professors.Professor;
import com.example.professors.dtos.ProfessorsDTO;
import com.example.professors.mappers.ProfessorsMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

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

    public ResponseData<List<DisciplinesDTO>> getAll(){
        List<DisciplinesSchema> disciplines = disciplinesRepository.findAll().list();
        List <DisciplinesDTO> disciplinesDTOS = disciplinesMapper.toDisciplinesDto(disciplines);

        if(disciplinesDTOS.isEmpty()){
            throw new DisciplineNotFound("Disciplines not found");
        }
        return new ResponseData<>(disciplinesDTOS, ResponseStatus.SUCCESS);
    }

    public ResponseData<DisciplinesDTO> getOne(UUID id){
        DisciplinesSchema discipline = this.disciplinesRepository.findById(id);
        if(discipline == null){
            throw new DisciplineNotFound("Discipline not found with id: " + id);
        }
        return new ResponseData<>(this.disciplinesMapper.toDto(discipline), ResponseStatus.SUCCESS, "Discipline found with id: " + id);
    }

    @Transactional
    public ResponseData<DisciplinesDTO> create(CreateDisciplineDTO dto){
        DisciplinesSchema discipline = this.disciplinesMapper.toDiscipline(dto);
        this.disciplinesRepository.create(discipline);
        return new ResponseData<>(this.disciplinesMapper.toDto(discipline), ResponseStatus.SUCCESS, "Discipline created");
    }

    @Transactional
    public ResponseData<DisciplinesDTO> update(UUID id, UpdateDisciplineDTO disciplinesDTO){
        DisciplinesSchema discipline = this.disciplinesRepository.findById(id);
        if(discipline == null){
            throw new DisciplineNotFound("Discipline not found with id: " + id);
        }
        this.disciplinesMapper.updateDisciplineFromDto(disciplinesDTO, discipline);
        this.disciplinesRepository.update(discipline);
        return new ResponseData<>(this.disciplinesMapper.toDto(discipline), ResponseStatus.SUCCESS, "Discipline updated");
    }

    @Transactional
    public ResponseData<Void> delete(UUID id){
        if(!this.disciplinesRepository.delete(id)){
            throw new DisciplineNotFound("Discipline not found with id " + id);
        }
        return new ResponseData<>(ResponseStatus.SUCCESS, "Discipline deleted");
    }

    public ResponseData<List<ProfessorsDTO>> getProfessorsByDisciplineId(UUID id){
        List<Professor> professors = this.disciplinesRepository.getProfessorsByDisciplineId(id);

        List<ProfessorsDTO> professorsDTOS = professorsMapper.toProfessorsDto(professors);
        return new ResponseData<>(professorsDTOS, ResponseStatus.SUCCESS, "idk what to write here :(");
    }
}
