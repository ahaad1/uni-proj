package com.example.student.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDTO {
    @NotBlank
    private String firstName;

    private String lastName;

    private String middleName;

    private Integer age;
}
