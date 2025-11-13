package com.example.discipline;

import com.example.common.enums.ResponseStatus;
import com.example.common.models.ResponseData;
import com.example.discipline.dtos.CreateDisciplineDTO;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.discipline.dtos.UpdateDisciplineDTO;
import com.example.discipline.exceptions.DisciplineNotFound;
import com.example.discipline.mappers.DisciplinesMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
public class DisciplinesServiceTest {
    @Inject
    DisciplineService disciplineService;

    @InjectMock
    DisciplinesRepository disciplinesRepository;

    @InjectMock
    DisciplinesMapper disciplinesMapper;

    private UUID disciplineId;
    private DisciplinesSchema disciplinesSchema;
    private DisciplinesDTO disciplinesDTO;
    private CreateDisciplineDTO createDisciplineDTO;

    @BeforeEach
    void setup(){
        disciplineId = UUID.randomUUID();

        disciplinesSchema = new DisciplinesSchema();
        disciplinesSchema.setName("Math");

        disciplinesDTO = new DisciplinesDTO();
        disciplinesDTO.setName("Math");

        createDisciplineDTO = new CreateDisciplineDTO();
        createDisciplineDTO.setName("Math");
    }

    @Test
    void getAll_WhenDisciplineExist_ShouldReturnSuccessResponse() {
        List<DisciplinesSchema> disciplines = List.of(disciplinesSchema);
        List<DisciplinesDTO> disciplineDTOS = List.of(disciplinesDTO);

        PanacheQuery<DisciplinesSchema> mockQuery = mock(PanacheQuery.class);
        when(disciplinesRepository.findAll()).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(disciplines);

        when(disciplinesMapper.toDisciplinesDto(disciplines)).thenReturn(disciplineDTOS);

        ResponseData<List<DisciplinesDTO>> result = disciplineService.getAll();

        assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(1, result.getData().size());
        assertEquals(disciplinesDTO, result.getData().getFirst());

        verify(disciplinesRepository).findAll();
        verify(mockQuery).list();
        verify(disciplinesMapper).toDisciplinesDto(disciplines);
    }

    @Test
    void getAllWhenNoDisciplines_ShouldThrowDisciplinesNotFound(){
        PanacheQuery<DisciplinesSchema> mockQuery = mock(PanacheQuery.class);

        when(disciplinesRepository.findAll()).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(List.of());

        DisciplineNotFound exception = assertThrows(DisciplineNotFound.class, () -> disciplineService.getAll());
        assertEquals("Disciplines not found", exception.getMessage());

        verify(disciplinesRepository).findAll();
        verify(mockQuery).list();
    }

    @Test
    void getOne_WhenDisciplineExist_ShouldReturnDiscipline() {
        when(disciplinesRepository.findById(disciplineId)).thenReturn(disciplinesSchema);
        when(disciplinesMapper.toDto(disciplinesSchema)).thenReturn(disciplinesDTO);

        ResponseData<DisciplinesDTO> result = disciplineService.getOne(disciplineId);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(disciplinesDTO, result.getData());

        verify(disciplinesRepository).findById(disciplineId);
        verify(disciplinesMapper).toDto(disciplinesSchema);
    }

    @Test
    void getOne_WhenDisciplineNotExists_ShouldThrowDisciplineNotFound() {
        when(disciplinesRepository.findById(disciplineId)).thenReturn(null);

        DisciplineNotFound exception = assertThrows(DisciplineNotFound.class, () -> disciplineService.getOne(disciplineId));

        assertEquals("Discipline not found with id: " + disciplineId, exception.getMessage());
        verify(disciplinesRepository).findById(disciplineId);
    }

    @Test
    void create_ShouldCreateAndReturnDiscipline() {
        when(disciplinesMapper.toDiscipline(createDisciplineDTO)).thenReturn(disciplinesSchema);
        when(disciplinesMapper.toDto(disciplinesSchema)).thenReturn(disciplinesDTO);
        when(disciplinesRepository.create(disciplinesSchema)).thenReturn(disciplinesSchema);

        ResponseData<DisciplinesDTO> result = disciplineService.create(createDisciplineDTO);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(disciplinesDTO, result.getData());

        verify(disciplinesMapper).toDiscipline(createDisciplineDTO);
        verify(disciplinesRepository).create(disciplinesSchema);
        verify(disciplinesMapper).toDto(disciplinesSchema);
    }

    @Test
    void update_WhenDisciplineExist_ShouldUpdateAndReturnDiscipline() {
        UpdateDisciplineDTO updateDiscipline = new UpdateDisciplineDTO();
        updateDiscipline.setName("Physics");

        DisciplinesDTO updatedDisciplineDTO = new DisciplinesDTO();
        updatedDisciplineDTO.setId(disciplineId);
        updatedDisciplineDTO.setName("Physics");

        when(disciplinesRepository.findById(disciplineId)).thenReturn(disciplinesSchema);
        doNothing().when(disciplinesMapper).updateDisciplineFromDto(updateDiscipline, disciplinesSchema);
        when(disciplinesRepository.update(disciplinesSchema)).thenReturn(disciplinesSchema);
        when(disciplinesMapper.toDto(disciplinesSchema)).thenReturn(updatedDisciplineDTO);

        ResponseData<DisciplinesDTO> result = disciplineService.update(disciplineId, updateDiscipline);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        assertEquals(updatedDisciplineDTO, result.getData());

        verify(disciplinesRepository).findById(disciplineId);
        verify(disciplinesMapper).updateDisciplineFromDto(updateDiscipline, disciplinesSchema);
        verify(disciplinesRepository).update(disciplinesSchema);
        verify(disciplinesMapper).toDto(disciplinesSchema);
    }

    @Test
    void update_WhenDisciplineNotExists_ShouldThrowDisciplineNotFound() {
        UpdateDisciplineDTO updateDiscipline = new UpdateDisciplineDTO();
        updateDiscipline.setName("Physics");

        when(disciplinesRepository.findById(disciplineId)).thenReturn(null);

        DisciplineNotFound exception = assertThrows(DisciplineNotFound.class,
                () -> disciplineService.update(disciplineId, updateDiscipline));

        assertEquals("Discipline not found with id: " + disciplineId, exception.getMessage());
        verify(disciplinesRepository).findById(disciplineId);
        verify(disciplinesRepository, never()).update(any());
    }

    @Test
    void delete_WhenDisciplineExists_ShouldDeleteAndReturnSuccess() {
        when(disciplinesRepository.delete(disciplineId)).thenReturn(true);

        ResponseData<Void> result = disciplineService.delete(disciplineId);

        Assertions.assertNotNull(result);
        assertEquals(ResponseStatus.SUCCESS.getCode(), result.getCode());
        verify(disciplinesRepository).delete(disciplineId);
    }

    @Test
    void delete_WhenDisciplineNotExists_ShouldThrowDisciplineNotFound() {
        when(disciplinesRepository.delete(disciplineId)).thenReturn(false);

        DisciplineNotFound exception = assertThrows(DisciplineNotFound.class, () -> disciplineService.delete(disciplineId));

        assertEquals("Discipline not found with id " + disciplineId, exception.getMessage());
        verify(disciplinesRepository).delete(disciplineId);
    }
}
