package com.example.professors;

import com.example.common.models.ResponseData;
import com.example.disciplines.dtos.DisciplinesDTO;
import com.example.disciplines.exceptions.DisciplineNotFound;
import com.example.professors.dtos.CreateProfessorDTO;
import com.example.professors.dtos.ProfessorsDTO;
import com.example.professors.dtos.UpdateProfessorDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorsController {
    @Inject
    ProfessorsService professorsService;

    @GET
    public ResponseData<List<ProfessorsDTO>> getAll(){
        return this.professorsService.getAll();
    }

    @GET
    @Path("/{id}")
    public ResponseData<ProfessorsDTO> getOne(@PathParam("id")UUID id) {

        return this.professorsService.getOne(id);
    }

    @POST
    public ResponseData<ProfessorsDTO> create(CreateProfessorDTO professor) {
        return this.professorsService.create(professor);
    }

    @PATCH
    @Path("/{id}")
    public ResponseData<ProfessorsDTO> update(@PathParam("id")UUID id, UpdateProfessorDto professor) {
        return this.professorsService.update(id, professor);
    }

    @DELETE
    @Path("/{id}")
    public ResponseData<Void> delete(@PathParam("id")UUID id) {
        return this.professorsService.delete(id);
    }

    @GET
    @Path("/{id}/discipline")
    public ResponseData<DisciplinesDTO> getDisciplineByProfessorId(@PathParam("id") UUID id) {
        return professorsService.getDisciplineByProfessorId(id);
    }

}
