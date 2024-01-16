package com.gridnine.testing;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public static final long TWO_HOURS = 1000*60*60L;

    private static final Logger logger = Logger.getLogger("FlightService");

    /**
     * <h2>totalTimeLanded</h2>
     * @param flight nullable
     * @return
     */
    public static long totalTimeLanded(Flight flight){

        if (flight == null) {throw new IllegalArgumentException("flight must not be null");}
        final List<Segment> segments = flight.getSegments();
        if (segments.isEmpty()) {
            throw new IllegalArgumentException("flight must contain at least one segment");}

        long ttl = 0L;
        long landedTime;
        if(segments.size() == 1) {return ttl;}
        for (int i = 1; i <= segments.size()-1 ; i++) {
            landedTime = getDateAndTimeInMillis(segments.get(i).getDepartureDate())
                    - getDateAndTimeInMillis(segments.get(i-1).getArrivalDate());
            ttl += landedTime;
        }

        logger.info("ttl=" + ttl);

        return ttl;
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
     * <h2>departureTimeIsCorrect</h2>
     * @param flight nullable
     * @return true if departure time is correct, i.e. departure time not earlier than now
     */
    public static boolean departureTimeIsCorrect(Flight flight){
        if (flight == null) {throw new IllegalArgumentException("Flight must not be null");}
        return (LocalDateTime.now().isBefore(flight.getSegments().get(0).getDepartureDate()));
    }
}
