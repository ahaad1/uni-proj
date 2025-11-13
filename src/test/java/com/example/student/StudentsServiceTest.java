package com.example.student;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import com.example.professor.Professor;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.professor.mappers.ProfessorsMapper;
import com.example.student.dtos.StudentDTO;
import com.example.student.exceptions.StudentNotFound;
import com.example.student.mappers.StudentsMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@QuarkusTest
public class StudentsServiceTest {
    @Inject
    StudentsService studentsService;

    @InjectMock
    StudentsRepository studentsRepository;

    @InjectMock
    StudentsMapper studentsMapper;

    @InjectMock
    ProfessorsMapper professorsMapper;

    private UUID studentId;
    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setup() {
        studentId = UUID.randomUUID();
        student = new Student();
        student.setId(studentId);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setMiddleName("Mikov");
        student.setAge(20);

        studentDTO = new StudentDTO();
        studentDTO.setId(studentId);
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");
        studentDTO.setMiddleName("Mikov");
        studentDTO.setAge(20);
    }


    @Test
    void getAll_WhenStudentExist_ShouldReturnSuccessResponse() {
        List<Student> students = List.of(student);
        List<StudentDTO> studentDTOs = List.of(studentDTO);


        when(studentsRepository.findAll()).thenReturn(students);
        when(studentsMapper.toStudentsDto(students)).thenReturn(studentDTOs);

        ResponseData<List<StudentDTO>> result = studentsService.getAll();

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(1, result.getData().size());
        assertEquals(studentDTO, result.getData().getFirst());

        verify(studentsRepository).findAll();
        verify(studentsMapper).toStudentsDto(students);
    }

    @Test
    void getAll_WhenNoStudents_ShouldThrowStudentNotFound(){
        when(studentsRepository.findAll()).thenReturn(List.of());

        StudentNotFound exception = assertThrows(StudentNotFound.class, () -> studentsService.getAll());

        assertEquals("Students not found", exception.getMessage());
        verify(studentsRepository).findAll();
    }

    @Test
    void getOne_WhenStudentExist_ShouldReturnStudent() {
        when(studentsRepository.findById(studentId)).thenReturn(student);
        when(studentsMapper.toDto(student)).thenReturn(studentDTO);

        ResponseData<StudentDTO> result = studentsService.getOne(studentId);

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(studentDTO, result.getData());

        verify(studentsRepository).findById(studentId);
        verify(studentsMapper).toDto(student);
    }

    @Test
    void getOne_WhenStudentNotExists_ShouldThrowStudentNotFound() {
        when(studentsRepository.findById(studentId)).thenReturn(null);

        StudentNotFound exception = assertThrows(StudentNotFound.class, () -> studentsService.getOne(studentId));

        assertEquals("Student with id " + studentId + " not found", exception.getMessage());
        verify(studentsRepository).findById(studentId);
    }

    @Test
    void create_ShouldCreateAndReturnStudent() {
        when(studentsMapper.toEntity(studentDTO)).thenReturn(student);
        when(studentsMapper.toDto(student)).thenReturn(studentDTO);
        when(studentsRepository.create(student)).thenReturn(student);

        ResponseData<StudentDTO> result = studentsService.create(studentDTO);

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(studentDTO, result.getData());

        verify(studentsMapper).toEntity(studentDTO);
        verify(studentsRepository).create(student);
        verify(studentsMapper).toDto(student);
    }

    @Test
    void update_WhenStudentExist_ShouldUpdateAndReturnStudent() {
        StudentDTO updateStudent = new StudentDTO();
        updateStudent.setFirstName("Jane");

        when(studentsRepository.findById(studentId)).thenReturn(student);
        doNothing().when(studentsMapper).updateStudentFromDto(updateStudent, student);
        when(studentsRepository.update(student)).thenReturn(student);
        when(studentsMapper.toDto(student)).thenReturn(updateStudent);

        ResponseData<StudentDTO> result = studentsService.update(studentId, updateStudent);

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(updateStudent, result.getData());

        verify(studentsRepository).findById(studentId);
        verify(studentsMapper).updateStudentFromDto(updateStudent, student);
        verify(studentsRepository).update(student);
        verify(studentsMapper).toDto(student);
    }


    @Test
    void update_WhenStudentNotExists_ShouldThrowStudentNotFound() {
        when(studentsRepository.findById(studentId)).thenReturn(null);

        StudentNotFound exception = assertThrows(StudentNotFound.class, () -> studentsService.update(studentId, studentDTO));

        assertEquals("Student not found: " + studentId, exception.getMessage());
        verify(studentsRepository).findById(studentId);
        verify(studentsRepository, never()).update(any());
    }

    @Test
    void delete_WhenStudentExists_ShouldDeleteAndReturnSuccess() {
        when(studentsRepository.delete(studentId)).thenReturn(true);

        ResponseData<Void> result = studentsService.delete(studentId);

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        verify(studentsRepository).delete(studentId);
    }

    @Test
    void delete_WhenStudentNotExists_ShouldThrowStudentNotFound() {
        when(studentsRepository.delete(studentId)).thenReturn(false);

        StudentNotFound exception = assertThrows(StudentNotFound.class, () -> studentsService.delete(studentId));

        assertEquals("Student with id " + studentId + " not found", exception.getMessage());
        verify(studentsRepository).delete(studentId);
    }

    @Test
    void getProfessorsByStudentId_ShouldReturnProfessorsList() {
        List<Professor> professors = List.of(new Professor());
        List<ProfessorsDTO> professorsDTOs = List.of(new ProfessorsDTO());

        when(studentsRepository.getProfessorsByStudentId(studentId)).thenReturn(professors);
        when(professorsMapper.toProfessorsDto(professors)).thenReturn(professorsDTOs);

        ResponseData<List<ProfessorsDTO>> result = studentsService.getProfessorsByStudentId(studentId);

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(professorsDTOs, result.getData());

        verify(studentsRepository).getProfessorsByStudentId(studentId);
        verify(professorsMapper).toProfessorsDto(professors);
    }
}
