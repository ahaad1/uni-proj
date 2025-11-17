package com.example.student.mappers;

import com.example.student.Student;
import com.example.student.dtos.CreateStudentDTO;
import com.example.student.dtos.StudentDTO;
import com.example.student.dtos.UpdateStudentDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface StudentsMapper {

    StudentDTO toDto(Student student);

    List<StudentDTO> toStudentsDto(List<Student> students);

    @Mapping(target = "id", ignore = true)
    Student toEntity(CreateStudentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(UpdateStudentDTO dto, @MappingTarget Student entity);
}
