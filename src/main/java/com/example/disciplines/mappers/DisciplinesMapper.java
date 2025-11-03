package com.example.disciplines.mappers;

import com.example.disciplines.DisciplinesSchema;
import com.example.disciplines.dtos.CreateDisciplineDTO;
import com.example.disciplines.dtos.DisciplinesDTO;
import com.example.disciplines.dtos.UpdateDisciplineDTO;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
