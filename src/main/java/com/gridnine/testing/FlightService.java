package com.gridnine.testing;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
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

    private static final Logger logger = Logger.getLogger("FlightService");

    private final List<Flight> flights;

    FlightService(List<Flight> flights){
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
        return new FlightService(flights.stream().filter(FlightService::flightArrivalIsAfterDeparture).toList());
    }

    public FlightService getFlightsNotDepartedYet(){
        return new FlightService(flights.stream().filter(FlightService::notDepartedYet).toList());
    }

    public static FlightService getFlightsNotDepartedYet(List<Flight> flights){
        throwIfNull(flights);
        return new FlightService(flights.stream().filter(FlightService::notDepartedYet).toList());
    }

    public static FlightService getFlightsWithArrivalAfterDeparture(List<Flight> flights){
        return new FlightService(getListOfFlightsWithArrivalAfterDeparture(flights));
    }

    /**<h2>public static List<Flight> getListOfFlightsWithArrivalAfterDeparture(List<Flight> flights)</h2>
     * Checks whether arrival time is after departure
     * @param flights nullable, method throws if null arg provided
     * @return list of flight with arrival time after departure
     */
    public static List<Flight> getListOfFlightsWithArrivalAfterDeparture(List<Flight> flights){
        return flights.stream().filter(FlightService::flightArrivalIsAfterDeparture).toList();
    }

    /**
     * <h2>public static boolean flightArrivalIsAfterDeparture(Flight flight)</h2>
     * @param flight nullable, method throws if null arg provided
     * @return true if flight has at least one segment and departure time of first segment
     * is before arrival time of last segment
     */
    public static boolean flightArrivalIsAfterDeparture(Flight flight){
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
    public static long totalTimeLanded(Flight flight){

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

        logger.info("totalTimeLanded=" + totalTimeLanded);

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
    public static boolean notDepartedYet(Flight flight){
        if (flight == null) {throw new IllegalArgumentException("Flight must not be null");}
        return LocalDateTime.now().isBefore(FlightService.getFlightDepartureTime(flight));
    }

    /**
     * <h2>public static LocalDateTime getFlightDepartureTime(Flight flight)</h2>
     * @param flight nullable
     * @return departure time of first segment
     * Throws if null arg provided or first segment is empty
     */
    public static LocalDateTime getFlightDepartureTime(Flight flight){
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
    public static LocalDateTime getFlightArrivalTime(Flight flight){
        throwIfNull(flight);
        throwIfNull(flight.getSegments());
        if (flight.getSegments().isEmpty()) {throw new IllegalArgumentException("Flight must have at least one segment");}
        int size = flight.getSegments().size();
        throwIfNull(flight.getSegments().get(size-1));
        return flight.getSegments().get(0).getArrivalDate();
    }

    /**
     * <h2>segmentIsCorrect</h2>
     * @param segment nullable
     * @return true if departure moment is before arrival moment, and departure time is not in the past
     */
    public static boolean segmentIsCorrect(Segment segment){
        throwIfNull(segment);
        return segment.getDepartureDate().isBefore(segment.getArrivalDate())
                &
                LocalDateTime.now().isBefore(segment.getDepartureDate());
    }

    public static boolean flightIsCorrect(Flight flight){
        boolean result = true;

        List<Segment> segments = flight.getSegments();

        // Make sure all segments are correct

        for (Segment s : segments ) {
            if (!result) {return false;}
        }

        // Make sure segment are not overlaid


        //TODO: complete flightIsCorrect method

        return result;
    }

    private static void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
    }
}
