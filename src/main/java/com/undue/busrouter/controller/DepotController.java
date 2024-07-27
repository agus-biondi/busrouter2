package com.undue.busrouter.controller;

import com.undue.busrouter.model.Depot;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/routing-problems/{routeProblemId}/depots")
public class DepotController {

    private final DataService dataService;

    @Autowired
    public DepotController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Depot> addDepotWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @RequestBody Depot depot) {
        Depot addedDepot = dataService.addDepotWithinRoutingProblem(routeProblemId, depot);
        return ResponseEntity.ok(addedDepot);
    }

    @PutMapping("/{depotId}")
    public ResponseEntity<Depot> updateDepotWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("depotId") String depotId,
            @RequestBody Depot updatedDepot) {
        if (!depotId.equals(updatedDepot.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Depot depot = dataService.updateDepotWithinRoutingProblem(routeProblemId, updatedDepot);
        return ResponseEntity.ok(depot);
    }

    @DeleteMapping("/{depotId}")
    public ResponseEntity<Void> deleteDepotWithinRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("depotId") String depotId) {
        dataService.deleteDepotWithinRoutingProblem(routeProblemId, depotId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Depot>> getAllDepotsFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId) {
        List<Depot> depots = dataService.getAllDepotsFromRoutingProblem(routeProblemId);
        return ResponseEntity.ok(depots);
    }

    @GetMapping("/{depotId}")
    public ResponseEntity<Depot> getDepotFromRoutingProblem(
            @PathVariable("routeProblemId") String routeProblemId,
            @PathVariable("depotId") String depotId) {
        Depot depot = dataService.getDepotFromRoutingProblem(routeProblemId, depotId);
        return ResponseEntity.ok(depot);
    }
}