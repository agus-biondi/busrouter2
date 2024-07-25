package com.undue.busrouter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutingProblem {

    private String routingProblemId;

    //Map Object ids to Object
    private final Map<String, School> schools = new HashMap<>();
    private final Map<String, BusStop> busStops = new HashMap<>();
    private final Map<String, Bus> buses = new HashMap<>();
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Depot> depots = new HashMap<>();

}
