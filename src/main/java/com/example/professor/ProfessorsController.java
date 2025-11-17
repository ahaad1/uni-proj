package com.example.professor;

import com.example.common.models.PageResult;
import com.example.common.models.ResponseData;
import com.example.discipline.dtos.DisciplinesDTO;
import com.example.professor.dtos.CreateProfessorDTO;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.professor.dtos.UpdateProfessorDto;
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
    public ResponseData<PageResult<ProfessorsDTO>> getAllPaged(@QueryParam("page") @DefaultValue("0") int page,
                                                               @QueryParam("size") @DefaultValue("20") int size){
        return this.professorsService.getAllPaged(page, size);
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
