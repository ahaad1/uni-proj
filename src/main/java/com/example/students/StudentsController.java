package com.example.students;


import com.example.common.models.ResponseData;
import com.example.professors.dtos.ProfessorsDTO;
import com.example.students.dtos.StudentDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentsController {

    @Inject
    StudentsService studentsService;


    @GET
    public ResponseData<List<StudentDTO>> getAll(){
        return this.studentsService.getAll();
    }

    @GET
    @Path("/{id}")
    public ResponseData<StudentDTO> getOne(@PathParam("id")UUID id) {
        return this.studentsService.getOne(id);
    }

    @POST
    public ResponseData<StudentDTO> create(StudentDTO studentDTO){
        return this.studentsService.create(studentDTO);
    }

    @PATCH
    @Path("/{id}")
    public ResponseData<StudentDTO> update(@PathParam("id")UUID id, StudentDTO studentDTO){
        return this.studentsService.update(id, studentDTO);
    }

    @DELETE
    @Path("/{id}")
    public ResponseData<Void> delete(@PathParam("id")UUID id){
        return this.studentsService.delete(id);
    }

    @GET
    @Path("/{id}/professors")
    public ResponseData<List<ProfessorsDTO>> getProfessorsByStudentId(@PathParam("id") UUID id) {
        return this.studentsService.getProfessorsByStudentId(id);
    }
}
