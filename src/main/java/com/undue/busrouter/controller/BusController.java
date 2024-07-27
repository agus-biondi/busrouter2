package com.undue.busrouter.controller;

import com.undue.busrouter.model.Bus;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routing-problems/{routeProblemId}/buses")
public class BusController {

    private final DataService dataService;

    @Autowired
    public BusController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Bus> addBusWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @RequestBody Bus bus) {
        Bus addedBus = dataService.addBusWithinRoutingProblem(routeProblemId, bus);
        return ResponseEntity.ok(addedBus);
    }

    @PutMapping("/{busId}")
    public ResponseEntity<Bus> updateBusWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busId") String busId,
            @RequestBody Bus updatedBus) {

        if(!busId.equals(updatedBus.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Bus addedBus = dataService.updateBusWithinRoutingProblem(routeProblemId, updatedBus);
        return ResponseEntity.ok(addedBus);

    }

    @DeleteMapping("/{busId}")
    public ResponseEntity<Void> deleteBusWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busId") String busId) {
        dataService.deleteBusWithinRoutingProblem(routeProblemId, busId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<Bus>> getAllBusesFromRoutingProblemId(
            @PathVariable("routeProblemId") String routeProblemId) {
        List<Bus> buses = dataService.getAllBusesFromRoutingProblem(routeProblemId);
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/{busId}")
    public ResponseEntity<Bus> getBusFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busId") String busId) {

        Bus bus = dataService.getBusFromRoutingProblem(routeProblemId, busId);
        return ResponseEntity.ok(bus);

    }

}