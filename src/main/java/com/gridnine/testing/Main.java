package com.gridnine.testing;

import java.util.List;

/**
 * <h2>Flight Filtering Demo Application</h2>
 * <h3>Please refer read-me.md file for detailed information</h3>
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("\nFlight Filtering Demo application\n");

        FlightService flightService = new FlightService(FlightBuilder.createFlights());

        List<Flight> flightList = flightService
                .printMessage("Test list of flights from is below")
                .printFlights()
                .printMessage("\nSelect not departed flights")
                .getFlightsNotDepartedYet()
                .printMessage("\nSelect flights correctly scheduled," +
                        " i.e. arrival moment is after departure as for entire flight so for each segment")
                .getFlightsWithArrivalAfterDeparture()
                .printMessage("\nSelect flights with total landed time within two hours")
                .getFlightServiceWithFLightNoMoreTwoHoursLanded()
                .getFlights();                                      // get List<Flight> for further use

        System.out.println("\nResult flightList = " + flightList);
    }
}