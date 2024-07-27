package com.undue.busrouter.controller;


import com.undue.busrouter.model.RoutingProblem;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routing-problems")
public class RoutingProblemController {


    private final DataService dataService;

    @Autowired
    public RoutingProblemController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/")
    public ResponseEntity<RoutingProblem> createEmptyRoutingProblem() {
        RoutingProblem problem = dataService.createRoutingProblem();
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/copy/{id}")
    public ResponseEntity<RoutingProblem> createRoutingProblemFrom(
            @PathVariable String sourceProblemId) {
        RoutingProblem newProblem = dataService.createRoutingProblemFrom(sourceProblemId);
        return ResponseEntity.ok(newProblem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutingProblem(@PathVariable String id) {
        dataService.deleteRoutingProblem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoutingProblem>> getAllRoutingProblems() {
        List<RoutingProblem> problems = dataService.getAllRoutingProblems();
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoutingProblem> getRoutingProblemById(@PathVariable String id) {
        RoutingProblem problem = dataService.getRoutingProblemById(id);
        return ResponseEntity.ok(problem);
    }

}
