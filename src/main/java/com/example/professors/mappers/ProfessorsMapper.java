package com.example.professors.mappers;

import com.example.professors.Professor;
import com.example.professors.dtos.CreateProfessorDTO;
import com.example.professors.dtos.ProfessorsDTO;
import com.example.professors.dtos.UpdateProfessorDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProfessorsMapper {
    ProfessorsDTO toDto(Professor professor);

    List<ProfessorsDTO> toProfessorsDto(List<Professor> professors);

    @Mapping(target = "id", ignore = true)
    Professor toEntity(ProfessorsDTO professorsDTO);

    Professor toEntity(CreateProfessorDTO createProfessorDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfessorFromDto(UpdateProfessorDto dto, @MappingTarget Professor entity);
}
