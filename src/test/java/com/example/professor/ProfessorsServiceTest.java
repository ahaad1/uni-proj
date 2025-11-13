package com.example.professor;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import com.example.discipline.DisciplinesRepository;
import com.example.discipline.DisciplinesSchema;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.discipline.mappers.DisciplinesMapper;
import com.example.professor.dtos.CreateProfessorDTO;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.professor.dtos.UpdateProfessorDto;
import com.example.professor.exceptions.ProfessorNotFound;
import com.example.professor.mappers.ProfessorsMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ProfessorsServiceTest {
    @Inject
    ProfessorsService professorsService;

    @InjectMock
    ProfessorsRepository professorsRepository;

    @InjectMock
    ProfessorsMapper professorsMapper;

    @InjectMock
    DisciplinesRepository disciplinesRepository;

    @InjectMock
    DisciplinesMapper disciplinesMapper;

    private UUID professorId;
    private Professor professor;
    private ProfessorsDTO professorsDTO;
    private CreateProfessorDTO createProfessorDTO;


    @BeforeEach
    void setup(){
        professorId = UUID.randomUUID();
        professor = new Professor();
        professor.setId(professorId);
        professor.setFirstName("John");
        professor.setLastName("Doe");
        professor.setMiddleName("Mikov");
        professor.setAge(20);

        professorsDTO = new ProfessorsDTO();
        professorsDTO.setId(professorId);
        professorsDTO.setFirstName("John");
        professorsDTO.setLastName("Doe");
        professorsDTO.setMiddleName("Mikov");
        professorsDTO.setAge(20);

        createProfessorDTO = new CreateProfessorDTO();
        createProfessorDTO.setFirstName("John");
        createProfessorDTO.setLastName("Doe");
        createProfessorDTO.setMiddleName("Mikov");
        createProfessorDTO.setAge(20);

    }

    @Test
    void getAll_WhenProfessorExist_ShouldReturnSuccessResponse() {
        List<Professor> professors = List.of(professor);
        List<ProfessorsDTO> professorsDTOs = List.of(professorsDTO);

        when(professorsRepository.findAll()).thenReturn(professors);
        when(professorsMapper.toProfessorsDto(professors)).thenReturn(professorsDTOs);

        ResponseData<List<ProfessorsDTO>> result = professorsService.getAll();

        assertNotNull(result);

        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(1, result.getData().size());
        assertEquals(professorsDTO, result.getData().getFirst());

        verify(professorsRepository).findAll();
        verify(professorsMapper).toProfessorsDto(professors);
    }

    @Test
    void getAll_WhenNoProfessors_ShouldThrowProfessorNotFound(){
        when(professorsRepository.findAll()).thenReturn(List.of());

        ProfessorNotFound exception = assertThrows(ProfessorNotFound.class, () -> professorsService.getAll());

        assertEquals("No professors found", exception.getMessage());
        verify(professorsRepository).findAll();
    }


    @Test
    void getOne_WhenProfessorExist_ShouldReturnProfessor() {
        when(professorsRepository.findById(professorId)).thenReturn(professor);
        when(professorsMapper.toDto(professor)).thenReturn(professorsDTO);

        ResponseData<ProfessorsDTO> result = professorsService.getOne(professorId);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(professorsDTO, result.getData());

        verify(professorsRepository).findById(professorId);
        verify(professorsMapper).toDto(professor);
    }

    @Test
    void getOne_WhenProfessorNotExists_ShouldThrowProfessorNotFound() {
        when(professorsRepository.findById(professorId)).thenReturn(null);

        ProfessorNotFound exception = assertThrows(ProfessorNotFound.class, () -> professorsService.getOne(professorId));

        assertEquals("Professor with id " + professorId + " not found", exception.getMessage());
        verify(professorsRepository).findById(professorId);
    }

    @Test
    void create_ShouldCreateAndReturnProfessor() {
        when(professorsMapper.toEntity(createProfessorDTO)).thenReturn(professor);
        when(professorsMapper.toDto(professor)).thenReturn(professorsDTO);
        when(professorsRepository.create(professor)).thenReturn(professor);

        ResponseData<ProfessorsDTO> result = professorsService.create(createProfessorDTO);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(professorsDTO, result.getData());

        verify(professorsMapper).toEntity(createProfessorDTO);
        verify(professorsRepository).create(professor);
        verify(professorsMapper).toDto(professor);
    }

    @Test
    void update_WhenProfessorExist_ShouldUpdateAndReturnProfessor() {
        UpdateProfessorDto updateProfessor = new UpdateProfessorDto();
        updateProfessor.setFirstName("Jane");

        ProfessorsDTO updatedProfessorDTO = new ProfessorsDTO();
        updatedProfessorDTO.setId(professorId);
        updatedProfessorDTO.setFirstName("Jane");
        updatedProfessorDTO.setLastName("Doe");
        updatedProfessorDTO.setMiddleName("Mikov");
        updatedProfessorDTO.setAge(20);

        when(professorsRepository.findById(professorId)).thenReturn(professor);
        doNothing().when(professorsMapper).updateProfessorFromDto(updateProfessor, professor);
        when(professorsRepository.update(professor)).thenReturn(professor);
        when(professorsMapper.toDto(professor)).thenReturn(updatedProfessorDTO);

        ResponseData<ProfessorsDTO> result = professorsService.update(professorId, updateProfessor);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(updatedProfessorDTO, result.getData());

        verify(professorsRepository).findById(professorId);
        verify(professorsMapper).updateProfessorFromDto(updateProfessor, professor);
        verify(professorsRepository).update(professor);
        verify(professorsMapper).toDto(professor);
    }

    @Test
    void update_WhenProfessorNotExists_ShouldThrowProfessorNotFound() {
        UpdateProfessorDto updateProfessorDto = new UpdateProfessorDto();
        updateProfessorDto.setFirstName("Jane");

        when(professorsRepository.findById(professorId)).thenReturn(null);

        ProfessorNotFound exception = assertThrows(ProfessorNotFound.class,
                () -> professorsService.update(professorId, updateProfessorDto));

        assertEquals("Professor with id " + professorId + " not found", exception.getMessage());
        verify(professorsRepository).findById(professorId);
        verify(professorsRepository, never()).update(any());
    }

    @Test
    void delete_WhenProfessorExists_ShouldDeleteAndReturnSuccess() {
        when(professorsRepository.delete(professorId)).thenReturn(true);

        ResponseData<Void> result = professorsService.delete(professorId);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        verify(professorsRepository).delete(professorId);
    }

    @Test
    void delete_WhenProfessorNotExists_ShouldThrowProfessorNotFound() {
        when(professorsRepository.delete(professorId)).thenReturn(false);

        ProfessorNotFound exception = assertThrows(ProfessorNotFound.class, () -> professorsService.delete(professorId));

        assertEquals("Professor with id " + professorId + " not found", exception.getMessage());
        verify(professorsRepository).delete(professorId);
    }

    @Test
    void getDisciplinesByProfessorId_ShouldReturnDiscipline() {
        DisciplinesSchema discipline = new DisciplinesSchema();
        DisciplinesDTO disciplinesDTO = new DisciplinesDTO();

        when(professorsRepository.findById(professorId)).thenReturn(professor);
        when(disciplinesRepository.getDisciplineByPofessor(professorId)).thenReturn(discipline);
        when(disciplinesMapper.toDto(discipline)).thenReturn(disciplinesDTO);

        ResponseData<DisciplinesDTO> result = professorsService.getDisciplineByProfessorId(professorId);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(disciplinesDTO, result.getData());

        verify(professorsRepository).findById(professorId);
        verify(disciplinesRepository).getDisciplineByPofessor(professorId);
        verify(disciplinesMapper).toDto(discipline);
    }
}
