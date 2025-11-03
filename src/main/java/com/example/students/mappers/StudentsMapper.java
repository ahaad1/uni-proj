package com.example.students.mappers;

import com.example.students.Student;
import com.example.students.dtos.StudentDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface StudentsMapper {
    StudentDTO toDto(Student student);

    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentDTO dto);

    List<StudentDTO> toStudentsDto(List<Student> students);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentDTO dto, @MappingTarget Student entity);
}