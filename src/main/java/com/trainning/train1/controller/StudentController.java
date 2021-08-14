package com.trainning.train1.controller;

import java.util.List;

import com.trainning.train1.entity.Student;
import com.trainning.train1.entity.Subject;
import com.trainning.train1.repository.StudentRepository;
import com.trainning.train1.repository.SubjectRepository;

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
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("")
    public ResponseEntity<?> getStudents() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable long id) {

        return studentRepository.findById(id).map((student) -> {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PostMapping("")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return new ResponseEntity<>("Add success! with id: " + student.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        return studentRepository.findById(id).map((student) -> {
            studentRepository.deleteById(id);
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        }).orElseGet(
                () -> new ResponseEntity<>("Delete failed: not found student with id: " + id, HttpStatus.NOT_FOUND));
    }

    @PutMapping("")
    public ResponseEntity<?> updateStudent(@RequestBody Student studentUpdate) {

        long id = studentUpdate.getId();

        return studentRepository.findById(id).map((student) -> {
            student.setPoint(studentUpdate.getPoint());
            student.setStudentName(studentUpdate.getStudentName());
            studentRepository.save(student);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }).orElseGet(() -> {
            return new ResponseEntity<>("Update failed: not found student with id: " + studentUpdate.getId(),
                    HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping("/{studentId}/subjects/{subjectId}")
    public ResponseEntity<?> updateStudentAddSubject(@PathVariable long subjectId, @PathVariable long studentId) {

        if (!subjectRepository.findById(subjectId).isPresent() || !studentRepository.findById(studentId).isPresent()) {
            return new ResponseEntity<>("Update failed!", HttpStatus.NOT_FOUND);

        }

        Subject subject = subjectRepository.findById(subjectId).get();
        Student student = studentRepository.findById(studentId).get();
        student.getSubjects().add(subject);
        studentRepository.save(student);
        return new ResponseEntity<>("Update success!", HttpStatus.OK);
    }

}
