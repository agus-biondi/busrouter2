package com.undue.busrouter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Depot implements Location {
    private String id;
    private String name;
    private double latitude;
    private double longitude;

    public void copyFrom(Depot other) {
        if (other == null) {
            throw new IllegalArgumentException("Source Depot cannot be null");
        }
        this.name = other.getName();
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
    }

    @Override
    public String toString() {
        return "Depot{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}