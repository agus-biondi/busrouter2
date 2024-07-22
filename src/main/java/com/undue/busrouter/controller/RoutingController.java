package com.undue.busrouter.controller;

import com.undue.busrouter.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/routing")
public class RoutingController {

    private final RoutingService routingService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<List<List<String>>> calculateRoutes() {
        List<List<String>> routes = routingService.calculateRoutes();
        return ResponseEntity.ok(routes);
    }
}