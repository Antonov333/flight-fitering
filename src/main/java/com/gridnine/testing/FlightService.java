package com.gridnine.testing;



/**
 * <h2>FlightService</h2>
 * Contains methods implementing criteria for filtering flights from test flight set
 */
public class FlightService {

    /**
     * <h2>totalTimeLanded</h2>
     * @param flight nullable
     * @return
     */
    public static long totalTimeLanded(Flight flight){
        if (flight == null) {throw new IllegalArgumentException("flight must not be null");}
        return -1L;
    }
}
