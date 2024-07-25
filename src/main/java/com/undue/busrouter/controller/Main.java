package com.undue.busrouter.controller;


import com.undue.busrouter.model.Bus;
import com.undue.busrouter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/")
public class Main {
    @GetMapping
    public ResponseEntity<String> serverStatus() {
        String message = "Server up!";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}