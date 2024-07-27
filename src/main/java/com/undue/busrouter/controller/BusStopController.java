package com.undue.busrouter.controller;

import com.undue.busrouter.model.BusStop;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routing-problems/{routeProblemId}/bus-stops")
public class BusStopController {

    private final DataService dataService;

    @Autowired
    public BusStopController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<BusStop> addBusStopWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @RequestBody BusStop busStop) {
        BusStop addedBusStop = dataService.addBusStopWithinRoutingProblem(routeProblemId, busStop);
        return ResponseEntity.ok(addedBusStop);
    }

    @PutMapping("/{busStopId}")
    public ResponseEntity<BusStop> updateBusStopWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busStopId") String busStopId,
            @RequestBody BusStop updatedBusStop) {
        if (!busStopId.equals(updatedBusStop.getId())) {
            return ResponseEntity.badRequest().build();
        }
        BusStop busStop = dataService.updateBusStopWithinRoutingProblem(routeProblemId, updatedBusStop);
        return ResponseEntity.ok(busStop);
    }

    @DeleteMapping("/{busStopId}")
    public ResponseEntity<Void> deleteBusStopWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busStopId") String busStopId) {
        dataService.deleteBusStopWithinRoutingProblem(routeProblemId, busStopId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BusStop>> getAllBusStopsFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId) {
        List<BusStop> busStops = dataService.getAllBusStopsFromRoutingProblem(routeProblemId);
        return ResponseEntity.ok(busStops);
    }

    @GetMapping("/{busStopId}")
    public ResponseEntity<BusStop> getBusStopFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busStopId") String busStopId) {
        BusStop busStop = dataService.getBusStopFromRoutingProblem(routeProblemId, busStopId);
        return ResponseEntity.ok(busStop);
    }
}