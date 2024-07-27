package com.undue.busrouter.service;

import com.undue.busrouter.exception.ResourceNotFoundException;
import com.undue.busrouter.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataService {

    private final Map<String, RoutingProblem> routingProblems = new ConcurrentHashMap<>();

    public RoutingProblem getRoutingProblemById(String routingProblemId) {
        RoutingProblem routingProblem = routingProblems.get(routingProblemId);
        if (routingProblem == null) {
            throw new ResourceNotFoundException("RoutingProblem", routingProblemId);
        }
        return routingProblem;
    }

    public Bus getBusFromRoutingProblem(String routingProblemId, String busId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        Bus bus = problem.getBuses().get(busId);
        if (bus == null) {
            throw new ResourceNotFoundException("Bus", busId);
        }
        return bus;
    }

    public List<Bus> getAllBusesFromRoutingProblem(String routingProblemId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        return new ArrayList<>(problem.getBuses().values());
    }

    public Bus addBusWithinRoutingProblem(String routingProblemId, Bus bus) {
        if (bus == null) {
            throw new IllegalArgumentException("Bus cannot be null");
        }
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        bus.setId(UUID.randomUUID().toString());
        problem.getBuses().put(bus.getId(), bus);
        return bus;
    }

    public Bus updateBusWithinRoutingProblem(String routingProblemId, Bus updatedBus) {
        if (updatedBus == null) {
            throw new IllegalArgumentException("Bus cannot be null");
        }
        Bus bus = getBusFromRoutingProblem(routingProblemId, updatedBus.getId());
        bus.copyFrom(updatedBus);
        return bus;
    }

    public void deleteBusWithinRoutingProblem(String routingProblemId, String busId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        if (problem.getBuses().remove(busId) == null) {
            throw new ResourceNotFoundException("Bus", busId);
        }
    }


    public BusStop getBusStopFromRoutingProblem(String routingProblemId, String busStopId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        BusStop busStop = problem.getBusStops().get(busStopId);
        if (busStop == null) {
            throw new ResourceNotFoundException("BusStop", busStopId);
        }
        return busStop;
    }

    public List<BusStop> getAllBusStopsFromRoutingProblem(String routingProblemId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        return new ArrayList<>(problem.getBusStops().values());
    }

    public BusStop addBusStopWithinRoutingProblem(String routingProblemId, BusStop busStop) {
        if (busStop == null) {
            throw new IllegalArgumentException("BusStop cannot be null");
        }
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        busStop.setId(UUID.randomUUID().toString());
        problem.getBusStops().put(busStop.getId(), busStop);
        return busStop;
    }

    public BusStop updateBusStopWithinRoutingProblem(String routingProblemId, BusStop updatedBusStop) {
        if (updatedBusStop == null) {
            throw new IllegalArgumentException("BusStop cannot be null");
        }
        BusStop busStop = getBusStopFromRoutingProblem(routingProblemId, updatedBusStop.getId());
        busStop.copyFrom(updatedBusStop);
        return busStop;
    }

    public void deleteBusStopWithinRoutingProblem(String routingProblemId, String busStopId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        if (problem.getBusStops().remove(busStopId) == null) {
            throw new ResourceNotFoundException("BusStop", busStopId);
        }
    }



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