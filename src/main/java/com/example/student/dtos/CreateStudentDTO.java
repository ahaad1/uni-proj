package com.example.student.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDTO {
    private String fistName;
    private String lastName;
    private String middleName;
    private Integer age;
}
