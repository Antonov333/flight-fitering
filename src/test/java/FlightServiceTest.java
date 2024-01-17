import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class FlightServiceTest {

    @Test
    public void checkConstants() {
        Assert.assertEquals(60 * 60 * 1000, FlightService.ONE_HOUR);
        Assert.assertEquals(FlightService.TWO_HOURS, FlightService.ONE_HOUR * 2);
    }

    private List<Flight> flightsList = FlightService.getTestFlightList();

    @Test
    public void printTestFlightList() {
        System.out.println(flightsList);
    }

    private FlightService getFlightService() {
        return new FlightService(flightsList);
    }

    @Test
    public void getFlightsTest() {
        FlightService flightService = new FlightService(flightsList);
        List<Flight> actualFlights = flightService.getFlights();
        Assertions.assertEquals(flightsList, actualFlights);
    }

    @Test
    public void getFlightsNotDepartedYetTest() {
        FlightService flightService = getFlightService();
        List<Flight> flightList = flightService.getFlightsNotDepartedYet().getFlights();
        Assert.assertEquals(5, flightList.size());
    }

    @Test
    public void printFlightsTest() {
        FlightService flightService = getFlightService();
        Assert.assertEquals(flightService, flightService.printFlights());
    }

    @Test
    public void getFlightServiceWithFLightNoMoreTwoHoursLandedTest() {
        System.out.println(getFlightService().getFlightServiceWithFLightNoMoreTwoHoursLanded().getFlights().size());
        Assert.assertEquals(4, getFlightService().getFlightServiceWithFLightNoMoreTwoHoursLanded().getFlights().size());
    }
}
