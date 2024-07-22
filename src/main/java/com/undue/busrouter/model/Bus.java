package com.undue.busrouter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bus implements CapacitatedVehicle {
    private String id;
    private String name;
    private int capacity;
    private String depotId;
}