package com.trainning.train1.controller;

import java.util.List;

import com.trainning.train1.entity.Classroom;
import com.trainning.train1.entity.Subject;
import com.trainning.train1.entity.Teacher;
import com.trainning.train1.repository.ClassroomRepository;
import com.trainning.train1.repository.SubjectRepository;
import com.trainning.train1.repository.TeacherRepository;

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
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    // GET

    @GetMapping("")
    public ResponseEntity<?> getTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable long id) {
        return teacherRepository.findById(id).map((teacher) -> {
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST

    @PostMapping("")
    public ResponseEntity<?> addTeacher(@RequestBody Teacher teacher) {
        teacherRepository.save(teacher);
        return new ResponseEntity<>("Add success!", HttpStatus.OK);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable long id) {
        return teacherRepository.findById(id).map((teacher) -> {
            teacherRepository.delete(teacher);
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        }).orElseGet(
                () -> new ResponseEntity<>("Delete failed: not found teacher with id: " + id, HttpStatus.NOT_FOUND));
    }

    // PUT

    @PutMapping("")
    public ResponseEntity<?> updateTeacher(@RequestBody Teacher teacherUpdate) {
        return teacherRepository.findById(teacherUpdate.getId()).map((teacher) -> {
            teacher.setTeacherName(teacherUpdate.getTeacherName());
            teacher.setGender(teacherUpdate.isGender());
            teacherRepository.save(teacher);
            return new ResponseEntity<>("Update success!", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>("Update failed: not found teacher with id: " + teacherUpdate.getId(),
                HttpStatus.OK));
    }

    @PutMapping("/{teacherId}/subjects/{subjectId}")
    public ResponseEntity<?> updateTeacherAddSubject(@PathVariable long teacherId, @PathVariable long subjectId) {

        if (!teacherRepository.findById(teacherId).isPresent() || !subjectRepository.findById(subjectId).isPresent()) {
            return new ResponseEntity<>(
                    "Update failed: not found teacher with id: " + teacherId + " or subject with id: " + subjectId,
                    HttpStatus.NOT_FOUND);
        }

        Subject subject = subjectRepository.findById(subjectId).get();
        Teacher teacher = teacherRepository.findById(teacherId).get();

        teacher.getSubjects().add(subject);
        teacherRepository.save(teacher);

        return new ResponseEntity<>("Update success!", HttpStatus.OK);
    }

    @PutMapping("/{teacherId}/classrooms/{classId}")
    public ResponseEntity<?> updateTeacherAddClass(@PathVariable long teacherId, @PathVariable long classId) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Classroom classroom = classroomRepository.findById(classId).get();

        teacher.getClassrooms().add(classroom);
        teacherRepository.save(teacher);

        return new ResponseEntity<>("Update success!", HttpStatus.OK);

    }

}
