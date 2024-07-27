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

    public Depot getDepotFromRoutingProblem(String routingProblemId, String depotId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        Depot depot = problem.getDepots().get(depotId);
        if (depot == null) {
            throw new ResourceNotFoundException("Depot", depotId);
        }
        return depot;
    }

    public List<Depot> getAllDepotsFromRoutingProblem(String routingProblemId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        return new ArrayList<>(problem.getDepots().values());
    }

    public Depot addDepotWithinRoutingProblem(String routingProblemId, Depot depot) {
        if (depot == null) {
            throw new IllegalArgumentException("Depot cannot be null");
        }
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        depot.setId(UUID.randomUUID().toString());
        problem.getDepots().put(depot.getId(), depot);
        return depot;
    }

    public Depot updateDepotWithinRoutingProblem(String routingProblemId, Depot updatedDepot) {
        if (updatedDepot == null) {
            throw new IllegalArgumentException("Depot cannot be null");
        }
        Depot depot = getDepotFromRoutingProblem(routingProblemId, updatedDepot.getId());
        depot.copyFrom(updatedDepot);
        return depot;
    }

    public void deleteDepotWithinRoutingProblem(String routingProblemId, String depotId) {
        RoutingProblem problem = getRoutingProblemById(routingProblemId);
        if (problem.getDepots().remove(depotId) == null) {
            throw new ResourceNotFoundException("Depot", depotId);
        }
    }

    public School getSchoolFromRoutingProblem(String routeProblemId, String schoolId) {
        RoutingProblem problem = getRoutingProblemById(routeProblemId);
        School school = problem.getSchools().get(schoolId);
        if (school == null) {
            throw new ResourceNotFoundException("School", schoolId);
        }
        return school;
    }

    public List<School> getAllSchoolsFromRoutingProblem(String routeProblemId) {
        RoutingProblem problem = getRoutingProblemById(routeProblemId);
        return new ArrayList<>(problem.getSchools().values());
    }

    public School addSchoolWithinRoutingProblem(String routeProblemId, School school) {
        if (school == null) {
            throw new IllegalArgumentException("School cannot be null");
        }
        RoutingProblem problem = getRoutingProblemById(routeProblemId);
        school.setId(UUID.randomUUID().toString());
        problem.getSchools().put(school.getId(), school);
        return school;
    }

    public School updateSchoolWithinRoutingProblem(String routeProblemId, School updatedSchool) {
        if (updatedSchool == null) {
            throw new IllegalArgumentException("School cannot be null");
        }
        School school = getSchoolFromRoutingProblem(routeProblemId, updatedSchool.getId());
        school.copyFrom(updatedSchool);
        return school;
    }

    public void deleteSchoolWithinRoutingProblem(String routeProblemId, String schoolId) {
        RoutingProblem problem = getRoutingProblemById(routeProblemId);
        if (problem.getSchools().remove(schoolId) == null) {
            throw new ResourceNotFoundException("School", schoolId);
        }
    }

    public Student getStudentFromRoutingProblem(String routProblemId, String studentId) {
        RoutingProblem problem = getRoutingProblemById(routProblemId);
        Student student = problem.getStudents().get(studentId);
        if (student == null) {
            throw new ResourceNotFoundException("Student", studentId);
        }
        return student;
    }

    public List<Student> getAllStudentsFromRoutingProblem(String routProblemId) {
        RoutingProblem problem = getRoutingProblemById(routProblemId);
        return new ArrayList<>(problem.getStudents().values());
    }

    public Student addStudentWithinRoutingProblem(String routProblemId, Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        RoutingProblem problem = getRoutingProblemById(routProblemId);
        student.setId(UUID.randomUUID().toString());
        problem.getStudents().put(student.getId(), student);
        return student;
    }

    public Student updateStudentWithinRoutingProblem(String routProblemId, Student updatedStudent) {
        if (updatedStudent == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        Student student = getStudentFromRoutingProblem(routProblemId, updatedStudent.getId());
        student.copyFrom(updatedStudent);
        return student;
    }

    public void deleteStudentWithinRoutingProblem(String routeProblemId, String studentId) {
        RoutingProblem problem = getRoutingProblemById(routeProblemId);
        if (problem.getStudents().remove(studentId) == null) {
            throw new ResourceNotFoundException("Student", studentId);
        }
    }

    public RoutingProblem createRoutingProblem() {
        RoutingProblem problem = new RoutingProblem();
        problem.setId(UUID.randomUUID().toString());
        routingProblems.put(problem.getId(), problem);
        return problem;
    }

    public RoutingProblem createRoutingProblemFrom(String sourceProblemId) {
        RoutingProblem sourceProblem = getRoutingProblemById(sourceProblemId);
        if (sourceProblem == null) {
            throw new ResourceNotFoundException("RoutingProblem", sourceProblemId);
        }

        RoutingProblem newProblem = new RoutingProblem();
        newProblem.setId(UUID.randomUUID().toString());
/*
        // Perform a deep copy of the source problem
        newProblem.setBuses(new HashMap<>(sourceProblem.getBuses()));
        newProblem.setBusStops(new HashMap<>(sourceProblem.getBusStops()));
        newProblem.setDepots(new HashMap<>(sourceProblem.getDepots()));
        newProblem.setSchools(new HashMap<>(sourceProblem.getSchools()));
        newProblem.setStudents(new HashMap<>(sourceProblem.getStudents()));
*/
        routingProblems.put(newProblem.getId(), newProblem);
        return newProblem;
    }

    public void deleteRoutingProblem(String id) {
        if (routingProblems.remove(id) == null) {
            throw new ResourceNotFoundException("RoutingProblem", id);
        }
    }

    public List<RoutingProblem> getAllRoutingProblems() {
        return new ArrayList<>(routingProblems.values());
    }

}