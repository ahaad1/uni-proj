package com.example.professor.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessorsDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
}
