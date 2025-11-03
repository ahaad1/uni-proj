package com.example.disciplines;

import com.example.common.models.ResponseData;
import com.example.disciplines.dtos.CreateDisciplineDTO;
import com.example.disciplines.dtos.DisciplinesDTO;
import com.example.disciplines.dtos.UpdateDisciplineDTO;
import com.example.professors.Professor;
import com.example.professors.dtos.ProfessorsDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;



@Path("/disciplines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DisciplinesController {
    @Inject
    DisciplineService disciplineService;

    @GET
    public ResponseData<List<DisciplinesDTO>> getAll() {
        return disciplineService.getAll();
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
    public ResponseData<DisciplinesDTO> update(@PathParam("id") UUID id, UpdateDisciplineDTO dto){
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
