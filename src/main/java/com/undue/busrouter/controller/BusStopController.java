package com.undue.busrouter.controller;

import com.undue.busrouter.model.BusStop;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busstops")
public class BusStopController {

    private final DataService dataService;

    @Autowired
    public BusStopController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<BusStop> addBusStop(@RequestBody BusStop busStop) {
        BusStop addedBusStop = dataService.addBusStop(busStop);
        return ResponseEntity.ok(addedBusStop);
    }

    @GetMapping
    public ResponseEntity<List<BusStop>> getAllBusStops() {
        List<BusStop> busStops = dataService.getAllBusStops();
        return ResponseEntity.ok(busStops);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusStop> getBusStop(@PathVariable String id) {
        BusStop busStop = dataService.getBusStop(id);
        if (busStop != null) {
            return ResponseEntity.ok(busStop);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}