package com.undue.busrouter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusStop implements Location {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private int studentCount;

    @Override
    public String toString() {
        return "BusStop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", studentCount=" + studentCount +
                '}';
    }
}