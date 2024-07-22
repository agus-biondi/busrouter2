package com.undue.busrouter.controller;

import com.undue.busrouter.model.Depot;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depots")
public class DepotController {

    private final DataService dataService;

    @Autowired
    public DepotController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Depot> addDepot(@RequestBody Depot depot) {
        Depot addedDepot = dataService.addDepot(depot);
        return ResponseEntity.ok(addedDepot);
    }

    @GetMapping
    public ResponseEntity<List<Depot>> getAllDepots() {
        List<Depot> depots = dataService.getAllDepots();
        return ResponseEntity.ok(depots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depot> getDepot(@PathVariable String id) {
        Depot depot = dataService.getDepot(id);
        if (depot != null) {
            return ResponseEntity.ok(depot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}