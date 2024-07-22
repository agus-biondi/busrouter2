package com.undue.busrouter.controller;

import com.undue.busrouter.model.School;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final DataService dataService;

    @Autowired
    public SchoolController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<School> addSchool(@RequestBody School school) {
        School addedSchool = dataService.addSchool(school);
        return ResponseEntity.ok(addedSchool);
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = dataService.getAllSchools();
        return ResponseEntity.ok(schools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchool(@PathVariable String id) {
        School school = dataService.getSchool(id);
        if (school != null) {
            return ResponseEntity.ok(school);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}