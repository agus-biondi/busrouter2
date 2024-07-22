package com.undue.busrouter.controller;

import com.undue.busrouter.model.*;
import com.undue.busrouter.service.DataService;
import com.undue.busrouter.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/test")
public class TestDataController {

    private final DataService dataService;
    private final RoutingService routingService;
    private final Random random = new Random();

    @Autowired
    public TestDataController(DataService dataService, RoutingService routingService) {
        this.dataService = dataService;
        this.routingService = routingService;
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearAllData() {
        dataService.clearAllData();
        return ResponseEntity.ok("All data cleared successfully");
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateTestData() {
        // Generate depots
        for (int i = 1; i <= 1; i++) {
            Depot depot = new Depot(null, "Depot " + i, 40 + (random.nextDouble() * 0.03d), -74 + (random.nextDouble() * 0.03d));
            dataService.addDepot(depot);
        }

        // Generate schools
        for (int i = 1; i <= 1; i++) {
            School school = new School(null, "School " + i, 40 + (random.nextDouble() * 0.03d), -74 + (random.nextDouble() * 0.03d));
            dataService.addSchool(school);
        }

        // Generate bus stops
        for (int i = 1; i <= 10; i++) {
            BusStop busStop = new BusStop(null, "Stop " + i, 40 + (random.nextDouble() * 0.03d), -74 + (random.nextDouble() * 0.03d), 0);
            dataService.addBusStop(busStop);
        }

        // Generate buses
        List<Depot> depots = dataService.getAllDepots();
        for (int i = 1; i <= 3; i++) {
            Depot randomDepot = depots.get(random.nextInt(depots.size()));
            Bus bus = new Bus(null, "Bus " + i, 30, randomDepot.getId());
            dataService.addBus(bus);
        }

        // Generate students and assign them to schools and bus stops
        List<School> schools = dataService.getAllSchools();
        List<BusStop> busStops = dataService.getAllBusStops();
        for (int i = 1; i <= 50; i++) {
            School randomSchool = schools.get(random.nextInt(schools.size()));
            BusStop randomStop = busStops.get(random.nextInt(busStops.size()));
            Student student = new Student(null, "Student " + i, randomStop.getId(), randomSchool.getId());
            dataService.addStudent(student);
        }

        return ResponseEntity.ok("Test data generated successfully");
    }

    @GetMapping("/visualizationData")
    public ResponseEntity<Map<String, Object>> getVisualizationData() {
        Map<String, Object> data = new HashMap<>();
        data.put("schools", dataService.getAllSchools());
        data.put("busStops", dataService.getAllBusStops());
        data.put("buses", dataService.getAllBuses());
        data.put("depots", dataService.getAllDepots());
        data.put("students", dataService.getAllStudents());

        List<List<String>> routes = routingService.calculateRoutes();
        data.put("routes", routes);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/calculateRoutes")
    public ResponseEntity<List<List<String>>> calculateRoutes() {
        List<List<String>> routes = routingService.calculateRoutes();
        return ResponseEntity.ok(routes);
    }
}