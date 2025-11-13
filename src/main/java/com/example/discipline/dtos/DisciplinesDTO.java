package com.example.discipline.dtos;

import com.example.discipline.DisciplinesSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinesDTO {
    private UUID id;
    private String name;

    public DisciplinesDTO(DisciplinesSchema discipline) {
        this.id = discipline.getId();
        this.name = discipline.getName();
    }
}
