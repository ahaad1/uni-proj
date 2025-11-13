package com.example.student;

import com.example.person.Person;
import com.example.professor.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student extends Person {

    @ManyToMany
//    @JsonManagedReference
    private List<Professor> professors;
}
