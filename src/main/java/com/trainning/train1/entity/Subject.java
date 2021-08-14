package com.trainning.train1.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String subjectName;

    @JsonIgnore
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<Student> students;

    @JsonIgnore
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<Teacher> teachers;

}
