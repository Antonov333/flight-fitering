package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

import static com.gridnine.testing.FlightService.TWO_HOURS;

public class Main {
    public static void main(String[] args) {

        List <Flight> setOfFlight = FlightBuilder.createFlights();
        System.out.println("setOfFlight.size() = " + setOfFlight.size());
        System.out.println(setOfFlight + "\n" + TWO_HOURS);

        setOfFlight = setOfFlight.stream()
                .filter(f -> FlightService.totalTimeLanded(f) <
                        TWO_HOURS).toList();
        System.out.println("setOfFlight.size() = " + setOfFlight.size());
        System.out.println(setOfFlight + "\n");



    }
}