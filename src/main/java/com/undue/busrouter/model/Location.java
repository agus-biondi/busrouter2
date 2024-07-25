package com.undue.busrouter.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Location {
    String getId();
    double getLatitude();
    double getLongitude();

    default double haversineDistanceTo(Location other) {
        double lat1 = Math.toRadians(this.getLatitude());
        double lon1 = Math.toRadians(this.getLongitude());
        double lat2 = Math.toRadians(other.getLatitude());
        double lon2 = Math.toRadians(other.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radius of earth in meters
        double r = 6371000;

        return c * r;
    }

    @Override
    String toString();

}