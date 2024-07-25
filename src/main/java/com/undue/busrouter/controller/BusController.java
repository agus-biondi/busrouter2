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
    public ResponseEntity<Bus> addBusToRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @RequestBody Bus bus) {
        Bus addedBus = dataService.addBusToRoutingProblem(routeProblemId, bus);
        return ResponseEntity.ok(addedBus);
    }

    @GetMapping
    public ResponseEntity<List<Bus>> getAllBusesForRoutingProblemId(
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
    @PutMapping("/{busId}")
    public ResponseEntity<Bus> updateBusForRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busId") String busId,
            @RequestBody Bus updatedBus) {

        if(!busId.equals(updatedBus.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Bus addedBus = dataService.updateBusForRoutingProblem(routeProblemId, updatedBus);
        return ResponseEntity.ok(addedBus);

    }

    @DeleteMapping("/{busId}")
    public ResponseEntity<Void> deleteBusFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("busId") String busId) {
        dataService.deleteBusFromRoutingProblem(routeProblemId, busId);
        return ResponseEntity.ok(null);
    }



}