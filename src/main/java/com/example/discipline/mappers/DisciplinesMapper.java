package com.example.discipline.mappers;

import com.example.discipline.DisciplinesSchema;
import com.example.discipline.dtos.CreateDisciplineDTO;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.discipline.dtos.UpdateDisciplineDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface DisciplinesMapper {
    DisciplinesDTO toDto(DisciplinesSchema discipline);

    @Mapping(target = "id", ignore = true)
    DisciplinesSchema toDiscipline(DisciplinesDTO disciplineDTO);

    DisciplinesSchema toDiscipline(CreateDisciplineDTO disciplineDTO);

    List<DisciplinesDTO> toDisciplinesDto(List<DisciplinesSchema> disciplinesSchemas);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDisciplineFromDto(UpdateDisciplineDTO dto, @MappingTarget DisciplinesSchema entity);
}
