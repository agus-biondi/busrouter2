package com.undue.busrouter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School implements Location {
    private String id;
    private String name;
    private double latitude;
    private double longitude;

    public void copyFrom(School other) {
        if (other == null) {
            throw new IllegalArgumentException("Source School cannot be null");
        }
        this.name = other.getName();
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
    }

    @Override
    public String toString() {
        return "School{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}