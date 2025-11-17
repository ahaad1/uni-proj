package com.example.student;


import com.example.common.models.PageResult;
import com.example.common.models.ResponseData;
import com.example.professor.dtos.ProfessorsDTO;
import com.example.student.dtos.CreateStudentDTO;
import com.example.student.dtos.StudentDTO;
import com.example.student.dtos.UpdateStudentDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentsController {

    @Inject
    StudentsService studentsService;


    @GET
    public ResponseData<PageResult<StudentDTO>> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size
    ) {
        return studentsService.getAllPaged(page, size);
    }

    @GET
    @Path("/{id}")
    public ResponseData<StudentDTO> getOne(@PathParam("id")UUID id) {
        return studentsService.getOne(id);
    }

    @POST
    public ResponseData<StudentDTO> create(CreateStudentDTO studentDTO){
        return studentsService.create(studentDTO);
    }

    @PATCH
    @Path("/{id}")
    public ResponseData<StudentDTO> update(@PathParam("id")UUID id, UpdateStudentDTO studentDTO){
        return studentsService.update(id, studentDTO);
    }

    @DELETE
    @Path("/{id}")
    public ResponseData<Void> delete(@PathParam("id")UUID id){
        return studentsService.delete(id);
    }

    @GET
    @Path("/{id}/professors")
    public ResponseData<List<ProfessorsDTO>> getProfessorsByStudentId(@PathParam("id") UUID id) {
        return studentsService.getProfessorsByStudentId(id);
    }
}
