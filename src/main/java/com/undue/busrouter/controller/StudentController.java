package com.undue.busrouter.controller;

import com.undue.busrouter.model.Student;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routing-problems/{routeProblemId}/students")
public class StudentController {

    private final DataService dataService;

    @Autowired
    public StudentController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudentWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @RequestBody Student student) {
        Student addedStudent = dataService.addStudentWithinRoutingProblem(routeProblemId, student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudentWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("studentId") String studentId,
            @RequestBody Student updatedStudent) {
        if (!studentId.equals(updatedStudent.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Student student = dataService.updateStudentWithinRoutingProblem(routeProblemId, updatedStudent);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudentWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("studentId") String studentId) {
        dataService.deleteStudentWithinRoutingProblem(routeProblemId, studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudentsFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId) {
        List<Student> students = dataService.getAllStudentsFromRoutingProblem(routeProblemId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("studentId") String studentId) {
        Student student = dataService.getStudentFromRoutingProblem(routeProblemId, studentId);
        return ResponseEntity.ok(student);
    }
}