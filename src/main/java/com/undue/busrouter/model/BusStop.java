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

    public void copyFrom(BusStop other) {
        if (other == null) {
            throw new IllegalArgumentException("Source BusStop cannot be null");
        }
        this.name = other.getName();
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
        this.studentCount = other.getStudentCount();
    }

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