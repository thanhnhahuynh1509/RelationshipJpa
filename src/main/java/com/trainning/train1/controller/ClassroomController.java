package com.trainning.train1.controller;

import java.util.List;

import com.trainning.train1.entity.Classroom;
import com.trainning.train1.entity.Student;
import com.trainning.train1.repository.ClassroomRepository;
import com.trainning.train1.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    StudentRepository studentRepository;

    // GET

    @GetMapping("")
    public ResponseEntity<?> getClassrooms() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return new ResponseEntity<>(classrooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassroom(@PathVariable long id) {
        return classroomRepository.findById(id).map((classroom) -> {
            return new ResponseEntity<>(classroom, HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    // ADD

    @PostMapping("")
    public ResponseEntity<?> addClassroom(@RequestBody Classroom classroom) {
        classroomRepository.save(classroom);
        return new ResponseEntity<>("Add classroom success! With id: " + classroom.getId(), HttpStatus.CREATED);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClassroom(@PathVariable long id) {
        return classroomRepository.findById(id).map((classroom) -> {
            classroomRepository.deleteById(id);
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        }).orElseGet(
                () -> new ResponseEntity<>("Delete failed: not found classroom with id: " + id, HttpStatus.NOT_FOUND));
    }

    // UPDATE

    @PutMapping("")
    public ResponseEntity<?> updateClassroom(@RequestBody Classroom classroom) {
        return classroomRepository.findById(classroom.getId()).map((cr) -> {
            cr.setName(classroom.getName());
            classroomRepository.save(cr);
            return new ResponseEntity<>("Update success!", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Update failed: not found classroom with id: " + classroom.getId(),
                HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{classId}/students/{studentId}")
    public ResponseEntity<?> updateClassroomAddStudent(@PathVariable long classId, @PathVariable long studentId) {
        Student student = studentRepository.findById(studentId).get();
        Classroom classroom = classroomRepository.findById(classId).get();

        student.setClassroom(classroom);
        studentRepository.save(student);

        return new ResponseEntity<>("Update success!", HttpStatus.OK);
    }

}
