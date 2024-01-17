package com.gridnine.testing;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**@author Sergei Antonov
 * <h2>FlightService</h2>
 * Contains methods implementing criteria for filtering flights from test flight set
 */
public class FlightService {
    /**
     * <h2>TWO_HOURS</h2>
     * constant equal to number of milliseconds in two hours
     */
    public static final long TWO_HOURS = 2*1000*60*60L;

    /**
     * <h2>ONE_HOUR</h2>
     * constant equal to number of milliseconds in one hour
     */
    public static final long ONE_HOUR = 3600000;
    private final List<Flight> flights;

    public FlightService(List<Flight> flights) {
        if (flights == null) {this.flights = new ArrayList<>();}
        else {this.flights = flights;}
    }

    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * <h2>method getFlightsWithArrivalAfterDeparture()</h2>
     * <b>No args</b>
     * @return instant of FlightService class containing list of correctly scheduled flights,
     * i.e. departure moment is before arrival
     */
    public FlightService getFlightsWithArrivalAfterDeparture(){
        return getFlightsWithArrivalAfterDeparture(flights);
    }

    /**
     * <h2>private static void printFlights(List<Flight> flights)</h2>
     *
     * @param flights If flights arg is null then throws.
     * Prints provided list of flights to console while non null arg provided
     */
    private static void printFlights(List<Flight> flights) {
        throwIfNull(flights);
        for (Flight f : flights) {
            System.out.println(f.toString());
        }
    }

    /**
     * <h2>public FlightService getFlightsNotDepartedYet()</h2>
     *
     * @return list of flights with departure time later than now
     */
    public FlightService getFlightsNotDepartedYet() {
        return FlightService.getFlightsNotDepartedYet(flights);
    }

    public FlightService printFlights() {
        printFlights(flights);
        return this;
    }

    private static FlightService getFlightsNotDepartedYet(List<Flight> flights) {
        throwIfNull(flights);
        List<Flight> flightList = getListOfFlightsNotDepartedYet(flights);
        return new FlightService(flightList);
    }

    private static List<Flight> getListOfFlightsNotDepartedYet(List<Flight> flights) {
        throwIfNull(flights);
        return flights.stream().filter(FlightService::notDepartedYet).toList();
    }

    /**
     * <h2>public static FlightService getFlightsWithArrivalAfterDeparture(List<Flight> flights))</h2>
     * <b>No args</b>
     * @return instant of FlightService class containing list of correctly scheduled flights,
     * i.e. departure moment is before arrival
     */
    private static FlightService getFlightsWithArrivalAfterDeparture(List<Flight> flights) {
        List<Flight> flightList = getListOfFlightsWithArrivalAfterDeparture(flights);
        return new FlightService(flightList);
    }

    /**<h2>public static List<Flight> getListOfFlightsWithArrivalAfterDeparture(List<Flight> flights)</h2>
     * Checks whether arrival time is after departure
     * @param flights nullable, method throws if null arg provided
     * @return list of flight with arrival time after departure
     */
    private static List<Flight> getListOfFlightsWithArrivalAfterDeparture(List<Flight> flights) {
        throwIfNull(flights);
        return flights.stream().filter(FlightService::allSegmentsArrAfterDep).toList();
    }

    /**
     * <h2>public FlightService getFlightServiceWithFLightNoMoreTwoHoursLanded()</h2>
     *
     * @return instance FlightService with list of flights with total time landed within 2 hours
     */
    public FlightService getFlightServiceWithFLightNoMoreTwoHoursLanded() {
        List<Flight> flightList = flights;
        flightList = flightList.stream()
                .filter(flight -> FlightService.totalTimeLanded(flight) < TWO_HOURS)
                .toList();
        return new FlightService(flightList);
    }

    public FlightService printMessage(String message) {
        if (!message.isEmpty()) {
            System.out.println(message);
        }
        return this;
    }

    /**
     * <h2>public static boolean flightArrivalIsAfterDeparture(Flight flight)</h2>
     * @param flight nullable, method throws if null arg provided
     * @return true if flight has at least one segment and departure time of first segment
     * is before arrival time of last segment
     */
    private static boolean flightArrivalIsAfterDeparture(Flight flight) {
        throwIfNull(flight);
        List<Segment> segmentList = flight.getSegments();
        int size = segmentList.size();
        if(size == 0) {return false;}
        return segmentList.get(0).getDepartureDate().isBefore(
                segmentList.get(size-1).getArrivalDate());
    }

    /**
     * <h2>totalTimeLanded</h2>
     * @param flight nullable
     * @return total time landed in milliseconds
     */
    private static long totalTimeLanded(Flight flight) {

        if (flight == null) {throw new IllegalArgumentException("flight must not be null");}
        final List<Segment> segments = flight.getSegments();
        if (segments.isEmpty()) {
            throw new IllegalArgumentException("flight must contain at least one segment");}

        long totalTimeLanded = 0L;
        long landedTime;
        if(segments.size() == 1) {return totalTimeLanded;}
        for (int i = 1; i <= segments.size()-1 ; i++) {
            landedTime = getDateAndTimeInMillis(segments.get(i).getDepartureDate())
                    - getDateAndTimeInMillis(segments.get(i-1).getArrivalDate());
            totalTimeLanded += landedTime;
        }

        return totalTimeLanded;
    }

    /**
     * <b>getDateAndTimeInMillis</b>
     * @param time nullable
     * @return time converted to milliseconds or -1 in case of null argument
     */
    private static long getDateAndTimeInMillis(LocalDateTime time){
        if (time == null) {return -1L;}
        return ZonedDateTime.of(time, ZoneId.systemDefault()).toInstant()
                .toEpochMilli();
    }

    /**
     * <h2>notDepartedYet</h2>
     * @param flight nullable
     * @return true if departure time is correct, i.e. departure time not earlier than now
     */
    private static boolean notDepartedYet(Flight flight) {
        if (flight == null) {throw new IllegalArgumentException("Flight must not be null");}
        return LocalDateTime.now().isBefore(FlightService.getFlightDepartureTime(flight));
    }

    /**
     * <h2>public static LocalDateTime getFlightDepartureTime(Flight flight)</h2>
     * @param flight nullable
     * @return departure time of first segment
     * Throws if null arg provided or first segment is empty
     */
    private static LocalDateTime getFlightDepartureTime(Flight flight) {
        throwIfNull(flight);
        throwIfNull(flight.getSegments());
        throwIfNull(flight.getSegments().get(0));
        return flight.getSegments().get(0).getDepartureDate();
    }

    /**
     * <h2>public static LocalDateTime getFlightDepartureTime(Flight flight)</h2>
     * @param flight nullable
     * @return departure time of first segment
     * Throws if null arg provided or first segment is empty
     */
    private static LocalDateTime getFlightArrivalTime(Flight flight) {
        throwIfNull(flight);
        throwIfNull(flight.getSegments());
        if (flight.getSegments().isEmpty()) {throw new IllegalArgumentException("Flight must have at least one segment");}
        int size = flight.getSegments().size();
        throwIfNull(flight.getSegments().get(size-1));
        return flight.getSegments().get(0).getArrivalDate();
    }

    /**
     * <h2>segmentIsCorrect</h2>
     * @param segment nullable, throws if null arg provided
     * @return true if departure moment is before arrival moment, and departure time is not past
     */
    private static boolean segmentScheduleIsCorrect(Segment segment) {
        throwIfNull(segment);
        return segmentArrAfterDep(segment) & segmentDepartureIsLaterThanNow(segment);
    }

    /**
     * <h2>segmentArrAfterDep</h2>
     * @param segment nullable
     * @return true if departure moment is before arrival moment
     */
    private static boolean segmentArrAfterDep(Segment segment){
        throwIfNull(segment);
        return segment.getDepartureDate().isBefore(segment.getArrivalDate());
    }

    /**
     * <h2>public static boolean flightAllSegmentsArrAfterDep(Flight flight)</h2>
     *
     * @param flight method throws if null arg provided
     * @return true if all segment of flight provided as argument has arrival time after departure,
     * and flight also has arrival moment after departure
     */
    private static boolean allSegmentsArrAfterDep(Flight flight){

        throwIfNull(flight);

        List<Segment> segments = flight.getSegments();

        for (Segment s : segments) {
            if (!segmentArrAfterDep(s)) {return false;
            }
        }

        return flightArrivalIsAfterDeparture(flight);
    }

    private static boolean segmentDepartureIsLaterThanNow(Segment segment) {
        throwIfNull(segment);
        return segment.getDepartureDate().isAfter(LocalDateTime.now());
    }

    private static void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
    }

    /**
     * As FlightBuilder.createFlights() method is not accesible from outside of com.gridnine.testing package,
     * so public getTestFlightList() method is created for testing purposes
     *
     * @return test list of flight created by FlightBuilder.createFlights()
     */
    public static List<Flight> getTestFlightList() {
        return FlightBuilder.createFlights();
    }
}
