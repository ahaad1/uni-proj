package com.example.professor.mappers;

import com.example.professor.Professor;
import com.example.professor.dtos.CreateProfessorDTO;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.professor.dtos.UpdateProfessorDto;
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
