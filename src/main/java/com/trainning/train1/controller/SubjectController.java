package com.trainning.train1.controller;

import java.util.List;

import com.trainning.train1.entity.Subject;
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
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    // GET

    @GetMapping("")
    public ResponseEntity<?> getSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubject(@PathVariable long id) {
        if (!subjectRepository.findById(id).isPresent())
            return new ResponseEntity<>("Id: " + id + " not found", HttpStatus.NOT_FOUND);
        Subject subject = subjectRepository.findById(id).get();
        return new ResponseEntity<>(subject, HttpStatus.OK);
        // return subjectRepository.findById(id).map((subject) -> {
        // return new ResponseEntity<>(subject, HttpStatus.OK);
        // }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ADD

    @PostMapping("")
    public ResponseEntity<?> addSubject(@RequestBody Subject subject) {
        subjectRepository.save(subject);
        return new ResponseEntity<>("Add success! With id: " + subject.getId(), HttpStatus.CREATED);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable long id) {
        // Subject subject = subjectRepository.findById(id).get();
        // if(subject == null)
        // return new ResponseEntity<>("delete failed", HttpStatus.NOT_FOUND);
        // subjectRepository.deleteById(id);
        // return new ResponseEntity<>("delete success", HttpStatus.OK);
        return subjectRepository.findById(id).map((subject) -> {
            subjectRepository.deleteById(id);
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        }).orElseGet(
                () -> new ResponseEntity<>("Delete failed: not found subject with id = " + id, HttpStatus.NOT_FOUND));
    }

    // UPDATE

    @PutMapping("")
    public ResponseEntity<?> updateSubject(@RequestBody Subject subjectUpdate) {
        // Subject subject = subjectRepository.findById(subjectUpdate.getId()).get();
        // if (subject == null)
        // return new ResponseEntity<>("delete failed", HttpStatus.NOT_FOUND);
        // subjectRepository.save(subjectUpdate);
        // return new ResponseEntity<>("delete success", HttpStatus.OK);

        return subjectRepository.findById(subjectUpdate.getId()).map((subject) -> {
            subject.setSubjectName(subjectUpdate.getSubjectName());
            subjectRepository.save(subject);
            return new ResponseEntity<>("Update success!", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Update failed: not found subject with id = " + subjectUpdate.getId(),
                HttpStatus.NOT_FOUND));
    }

}
