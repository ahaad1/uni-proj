package com.example.professors;

import com.example.disciplines.DisciplinesSchema;
import com.example.person.Person;
import com.example.students.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professors")
@Getter
@Setter
@NoArgsConstructor
public class Professor extends Person {
    @ManyToOne
    @JsonBackReference
    private DisciplinesSchema discipline;

    @ManyToMany(mappedBy = "professors")
    @JsonBackReference
    private List<Student> students;
}
