package com.undue.busrouter.controller;

import com.undue.busrouter.model.School;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routing-problems/{routeProblemId}/schools")
public class SchoolController {

    private final DataService dataService;

    @Autowired
    public SchoolController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<School> addSchoolWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @RequestBody School school) {
        School addedSchool = dataService.addSchoolWithinRoutingProblem(routeProblemId, school);
        return ResponseEntity.ok(addedSchool);
    }

    @PutMapping("/{schoolId}")
    public ResponseEntity<School> updateSchoolWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("schoolId") String schoolId,
            @RequestBody School updatedSchool) {
        if (!schoolId.equals(updatedSchool.getId())) {
            return ResponseEntity.badRequest().build();
        }
        School school = dataService.updateSchoolWithinRoutingProblem(routeProblemId, updatedSchool);
        return ResponseEntity.ok(school);
    }

    @DeleteMapping("/{schoolId}")
    public ResponseEntity<Void> deleteSchoolWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("schoolId") String schoolId) {
        dataService.deleteSchoolWithinRoutingProblem(routeProblemId, schoolId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchoolsFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId) {
        List<School> schools = dataService.getAllSchoolsFromRoutingProblem(routeProblemId);
        return ResponseEntity.ok(schools);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<School> getSchoolFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("schoolId") String schoolId) {
        School school = dataService.getSchoolFromRoutingProblem(routeProblemId, schoolId);
        return ResponseEntity.ok(school);
    }
}