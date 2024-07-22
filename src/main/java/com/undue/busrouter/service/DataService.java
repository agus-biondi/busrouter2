package com.undue.busrouter.service;

import com.undue.busrouter.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataService {
    private final Map<String, School> schools = new HashMap<>();
    private final Map<String, BusStop> busStops = new HashMap<>();
    private final Map<String, Bus> buses = new HashMap<>();
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Depot> depots = new HashMap<>();

    public <T> T add(T item) {
        String id = UUID.randomUUID().toString();
        if (item instanceof School school) {
            school.setId(id);
            schools.put(id, school);
        } else if (item instanceof BusStop busStop) {
            busStop.setId(id);
            busStops.put(id, busStop);
        } else if (item instanceof Bus bus) {
            bus.setId(id);
            buses.put(id, bus);
        } else if (item instanceof Student student) {
            student.setId(id);
            students.put(id, student);
            updateBusStopStudentCount(student.getBusStopId(), 1);
        } else if (item instanceof Depot depot) {
            depot.setId(id);
            depots.put(id, depot);
        }
        return item;
    }

    private void updateBusStopStudentCount(String busStopId, int delta) {
        BusStop busStop = busStops.get(busStopId);
        if (busStop != null) {
            busStop.setStudentCount(busStop.getStudentCount() + delta);
        }
    }

    public School addSchool(School school) {
        return add(school);
    }

    public BusStop addBusStop(BusStop busStop) {
        return add(busStop);
    }

    public Bus addBus(Bus bus) {
        return add(bus);
    }

    public Student addStudent(Student student) {
        return add(student);
    }

    public List<School> getAllSchools() {
        return new ArrayList<>(schools.values());
    }

    public List<BusStop> getAllBusStops() {
        return new ArrayList<>(busStops.values());
    }

    public List<Bus> getAllBuses() {
        return new ArrayList<>(buses.values());
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public School getSchool(String id) {
        return schools.get(id);
    }

    public BusStop getBusStop(String id) {
        return busStops.get(id);
    }

    public Bus getBus(String id) {
        return buses.get(id);
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    public Depot addDepot(Depot depot) {
        return add(depot);
    }

    public List<Depot> getAllDepots() {
        return new ArrayList<>(depots.values());
    }

    public Depot getDepot(String id) {
        return depots.get(id);
    }

    public void clearAllData() {
        schools.clear();
        busStops.clear();
        buses.clear();
        students.clear();
        depots.clear();
    }
}