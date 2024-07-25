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
    private String depotId;

    private int capacity;

    public void copyFrom(Bus otherBus) {
        if (otherBus == null) {
            throw new IllegalArgumentException("Source Bus cannot be null");
        }
        name = otherBus.getName();
        depotId = otherBus.getDepotId();
        capacity = otherBus.getCapacity();
    }
}