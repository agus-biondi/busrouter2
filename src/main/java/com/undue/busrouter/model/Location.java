package com.undue.busrouter.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Location {
    String getId();
    double getLatitude();
    double getLongitude();

    @Override
    String toString();

}