package com.example.discipline;

import com.example.common.models.PageResult;
import com.example.common.models.ResponseData;
import com.example.discipline.dtos.CreateDisciplineDTO;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.discipline.dtos.UpdateDisciplineDTO;
import com.example.professor.dtos.ProfessorsDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;



@Path("/disciplines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DisciplinesController {
    @Inject
    DisciplineService disciplineService;

    @GET
    public ResponseData<PageResult<DisciplinesDTO>> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size
    ) {
        return disciplineService.getAll(page, size);
    }

    @GET
    @Path("/{id}")
    public ResponseData<DisciplinesDTO> getOne(@PathParam("id") UUID id) {
        return disciplineService.getOne(id);
    }

    @POST
    public ResponseData<DisciplinesDTO> create(CreateDisciplineDTO dto){
        return disciplineService.create(dto);
    }

    @PATCH
    @Path("/{id}")
    public ResponseData<DisciplinesDTO> update(@PathParam("id") UUID id,
                                               UpdateDisciplineDTO dto){
        return disciplineService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public ResponseData<Void> delete(@PathParam("id") UUID id) {
        return disciplineService.delete(id);
    }

    @GET
    @Path("/{id}/professors")
    public ResponseData<List<ProfessorsDTO>> getProfessorsByDiscipline(@PathParam("id") UUID id){
        return disciplineService.getProfessorsByDisciplineId(id);
    }
}
