package com.example.professors.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateProfessorDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
}
