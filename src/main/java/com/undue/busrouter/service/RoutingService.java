package com.undue.busrouter.service;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.google.protobuf.Duration;
import com.undue.busrouter.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutingService {

    private final DataService dataService;

    public RoutingService(DataService dataService) {
        this.dataService = dataService;
        Loader.loadNativeLibraries();
    }

    public List<List<String>> calculateRoutes() {
        // Gather data
        List<Location> locations = new ArrayList<>();
        locations.addAll(dataService.getAllDepots());
        locations.addAll(dataService.getAllSchools());
        locations.addAll(dataService.getAllBusStops());

        Map<String, Integer> mapLocationIdsToLocationIndex = new HashMap<>();
        for (int i = 0; i < locations.size(); i++) {
            mapLocationIdsToLocationIndex.put(locations.get(i).getId(), i);
        }

        List<Bus> buses = dataService.getAllBuses();
        List<Student> students = dataService.getAllStudents();

        int vehicleNumber = buses.size();
        int nodeNumber = locations.size();

        // Print problem size
        System.out.println("Locations: " + locations.size());
        System.out.println("Buses: " + buses.size());
        System.out.println("Students: " + students.size());

        // Create distance matrix
        long[][] distanceMatrix = createDistanceMatrix(locations);

        System.out.println("Locations:");
        for (Location l : locations) {
            System.out.println(l.toString());
        }
        System.out.println("Distance Matrix: ");
        for (long[] distance : distanceMatrix) {
            System.out.println(Arrays.toString(distance));
        }

        // Create demands array
        long[] demands = createDemands(locations, students);
        System.out.println("Demands: " + Arrays.toString(demands));



        // Create Routing Index Manager
        RoutingIndexManager manager = new RoutingIndexManager(nodeNumber, vehicleNumber, 0);
        // Create Routing Model
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return distanceMatrix[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Distance constraint.
        routing.addDimension(transitCallbackIndex, 0, 400_000,
                true, // start cumul to zero
                "Distance");
        RoutingDimension distanceDimension = routing.getMutableDimension("Distance");
        distanceDimension.setGlobalSpanCostCoefficient(100);

        int[][] pickupsDeliveries = new int[students.size()][2];
        for (int i = 0; i < students.size(); i++) {
            pickupsDeliveries[i][0] = mapLocationIdsToLocationIndex.get(students.get(i).getSchoolId());
            pickupsDeliveries[i][1] = mapLocationIdsToLocationIndex.get(students.get(i).getBusStopId());
        }

        // Define Transportation Requests.
        Solver solver = routing.solver();
        for (int[] request : pickupsDeliveries) {
            long pickupIndex = manager.nodeToIndex(request[0]);
            long deliveryIndex = manager.nodeToIndex(request[1]);
            routing.addPickupAndDelivery(pickupIndex, deliveryIndex);
            solver.addConstraint(
                    solver.makeEquality(routing.vehicleVar(pickupIndex), routing.vehicleVar(deliveryIndex)));
            solver.addConstraint(solver.makeLessOrEqual(
                    distanceDimension.cumulVar(pickupIndex), distanceDimension.cumulVar(deliveryIndex)));
        }


        // Setting first solution heuristic
        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters()
                .toBuilder()
                .setFirstSolutionStrategy((FirstSolutionStrategy.Value.PARALLEL_CHEAPEST_INSERTION))
                .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                .setTimeLimit(Duration.newBuilder().setSeconds(3).build())
                .build();

        // Solve the problem
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Extract solution
        if (solution == null) {
            System.out.println("No solution found.");
            return Collections.emptyList();
        }

        return extractSolution(routing, manager, solution, locations);
    }

    private long[] createDemands(List<Location> locations, List<Student> students) {
        long[] demands = new long[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            if (location instanceof School) {
                demands[i] = students.stream().filter(s -> s.getSchoolId().equals(location.getId())).count();
            } else if (location instanceof BusStop) {
                demands[i] = -students.stream().filter(s -> s.getBusStopId().equals(location.getId())).count();
            }
        }
        return demands;
    }

    private long[][] createDistanceMatrix(List<Location> locations) {
        int size = locations.size();
        long[][] matrix = new long[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    matrix[i][j] = calculateDistance(locations.get(i), locations.get(j));
                }
            }
        }
        return matrix;
    }

    private long calculateDistance(Location a, Location b) {
        final int R = 6371000; // Radius of the Earth in meters

        double latDistance = Math.toRadians(b.getLatitude() - a.getLatitude());
        double lonDistance = Math.toRadians(b.getLongitude() - a.getLongitude());
        double aLat = Math.toRadians(a.getLatitude());
        double bLat = Math.toRadians(b.getLatitude());

        double haversine = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(aLat) * Math.cos(bLat)
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));

        return Math.round(R * c); // Distance in meters, rounded to the nearest whole number
    }

    private List<List<String>> extractSolution(RoutingModel routing, RoutingIndexManager manager,
                                               Assignment solution, List<Location> locations) {
        List<List<String>> routes = new ArrayList<>();
        long totalDistance = 0;
        for (int i = 0; i < routing.vehicles(); ++i) {
            List<String> route = new ArrayList<>();
            long index = routing.start(i);
            long routeDistance = 0;
            while (!routing.isEnd(index)) {
                long nodeIndex = manager.indexToNode(index);
                route.add(locations.get((int) nodeIndex).getId());
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }
            route.add(locations.get(manager.indexToNode(index)).getId());
            routes.add(route);
            totalDistance += routeDistance;
            System.out.println("Route for vehicle " + i + ": " + String.join(" -> ", route));
            System.out.println("Route distance: " + routeDistance);
            System.out.println();
        }
        System.out.println("Total distance of all routes: " + totalDistance);
        return routes;
    }
}