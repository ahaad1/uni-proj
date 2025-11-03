package com.example.students;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import com.example.professors.Professor;
import com.example.professors.dtos.ProfessorsDTO;
import com.example.professors.mappers.ProfessorsMapper;
import com.example.students.dtos.StudentDTO;
import com.example.students.exceptions.StudentNotFound;
import com.example.students.mappers.StudentsMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StudentsService {
    @Inject
    StudentsRepository studentsRepository;

    @Inject
    StudentsMapper studentsMapper;

    @Inject
    ProfessorsMapper professorsMapper;


    public ResponseData<List<StudentDTO>> getAll(){
        List<StudentDTO> students = studentsMapper.toStudentsDto(studentsRepository.findAll());
        if(students.isEmpty()) {
            throw new StudentNotFound("Students not found");
        }
        return new ResponseData<>(students, ResponseStatus.SUCCESS, "Students retrieved successfully");
    }

    public ResponseData<StudentDTO> getOne(UUID id) {
        Student student = this.studentsRepository.findById(id);
        if(student == null) {
            throw new StudentNotFound("Student with id " + id + " not found");
        }
        return new ResponseData<>(this.studentsMapper.toDto(student), ResponseStatus.SUCCESS, "Student retrieved successfully");
    }

    @Transactional
    public ResponseData<StudentDTO> create(StudentDTO studentDTO) {
        Student student = this.studentsMapper.toEntity(studentDTO);
        this.studentsRepository.create(student);
        return new ResponseData<>(this.studentsMapper.toDto(student), ResponseStatus.SUCCESS, "Student created successfully");
    }

    @Transactional
    public ResponseData<StudentDTO> update(UUID id, StudentDTO studentDTO) {
        Student existing = this.studentsRepository.findById(id);
        if (existing == null) {
            throw new StudentNotFound("Student not found: " + id);
        }

        this.studentsMapper.updateStudentFromDto(studentDTO, existing);
        Student merged = this.studentsRepository.update(existing);
        return new ResponseData<>(studentsMapper.toDto(merged), ResponseStatus.SUCCESS, "Student updated successfully");
    }

    @Transactional
    public ResponseData<Void> delete(UUID id) {
        if(!this.studentsRepository.delete(id)) {
            throw new StudentNotFound("Student with id " + id + " not found");
        }
        return new ResponseData<>(ResponseStatus.SUCCESS, "Student deleted successfully");
    }

    public ResponseData<List<ProfessorsDTO>> getProfessorsByStudentId(UUID studentId) {
        List<ProfessorsDTO> professors = professorsMapper.toProfessorsDto(studentsRepository.getProfessorsByStudentId(studentId));
        return new ResponseData<>(professors, ResponseStatus.SUCCESS, "Student's professors retrieved successfully");
    }
}
