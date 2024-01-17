package com.gridnine.testing;

import java.util.List;

import static com.gridnine.testing.FlightService.TWO_HOURS;

public class Main {
    public static void main(String[] args) {
// Select only correct landed time flights
        List <Flight> setOfFlight = FlightBuilder.createFlights();
        System.out.println("setOfFlight.size() = " + setOfFlight.size());
        System.out.println(setOfFlight + "\n" + TWO_HOURS);

        setOfFlight = setOfFlight.stream()
                .filter(f -> FlightService.totalTimeLanded(f) <
                        TWO_HOURS).toList();
        System.out.println("setOfFlight.size() = " + setOfFlight.size());
        System.out.println(setOfFlight + "\n");

// Select flights with departure correctly scheduled
        FlightService flightService = new FlightService(FlightBuilder.createFlights());
        setOfFlight = flightService.getFlightsWithArrivalAfterDeparture()
                .getFlightServiceWithFLightNoMoreTwoHoursLanded().getFlightsNotDepartedYet().getFlights();





    }
}