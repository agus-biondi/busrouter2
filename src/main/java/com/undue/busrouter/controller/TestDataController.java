package com.undue.busrouter.controller;

import com.undue.busrouter.model.*;
import com.undue.busrouter.service.DataService;
import com.undue.busrouter.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/api/test-data")
public class TestDataController {

    private final DataService dataService;
    private final RoutingService routingService;
    private final Random random = new Random();

    @Autowired
    public TestDataController(DataService dataService, RoutingService routingService) {
        this.dataService = dataService;
        this.routingService = routingService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateTestData() {
        String routingProblemId = dataService.createRoutingProblem().getId();

        // Generate depots
        for (int i = 1; i <= 1; i++) {
            Depot depot = new Depot(null, "Depot " + i, 40 + (random.nextDouble() * 0.03d), -74 + (random.nextDouble() * 0.03d));
            dataService.addDepotWithinRoutingProblem(routingProblemId, depot);
        }

        // Generate schools
        for (int i = 1; i <= 1; i++) {
            School school = new School(null, "School " + i, 40 + (random.nextDouble() * 0.03d), -74 + (random.nextDouble() * 0.03d));
            dataService.addSchoolWithinRoutingProblem(routingProblemId, school);
        }

        // Generate bus stops
        for (int i = 1; i <= 50; i++) {
            BusStop busStop = new BusStop(null, "Stop " + i, 40 + (random.nextDouble() * 0.03d), -74 + (random.nextDouble() * 0.03d), 0);
            dataService.addBusStopWithinRoutingProblem(routingProblemId, busStop);
        }

        // Generate buses
        List<Depot> depots = dataService.getAllDepotsFromRoutingProblem(routingProblemId);
        for (int i = 1; i <= 10; i++) {
            Depot randomDepot = depots.get(random.nextInt(depots.size()));
            Bus bus = new Bus(null, "Bus " + i, randomDepot.getId(), 60);
            dataService.addBusWithinRoutingProblem(routingProblemId, bus);
        }

        // Generate students and assign them to schools and bus stops
        List<School> schools = dataService.getAllSchoolsFromRoutingProblem(routingProblemId);
        List<BusStop> busStops = dataService.getAllBusStopsFromRoutingProblem(routingProblemId);
        for (int i = 1; i <= 450; i++) {
            School randomSchool = schools.get(random.nextInt(schools.size()));
            BusStop randomStop = busStops.get(random.nextInt(busStops.size()));
            Student student = new Student(null, "Student " + i, randomStop.getId(), randomSchool.getId());
            dataService.addStudentWithinRoutingProblem(routingProblemId, student);
        }

        return ResponseEntity.ok("Test data generated successfully for Routing Problem: " + routingProblemId);
    }

}

