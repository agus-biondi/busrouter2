package com.undue.busrouter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
    private String busStopId;
    private String schoolId;

    public void copyFrom(Student other) {
        if (other == null) {
            throw new IllegalArgumentException("Source Student cannot be null");
        }
        this.name = other.getName();
        this.busStopId = other.getBusStopId();
        this.schoolId = other.getSchoolId();
    }
}