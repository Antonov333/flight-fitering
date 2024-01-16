import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import com.gridnine.testing.FlightService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

public class MainTest {
    @Test
    public void totalTimeLandedTest(){

        Assert.assertThrows(IllegalAccessError.class,() -> {
            FlightService.totalTimeLanded(null);
        });
    }

    @Test
    public void checkConstants(){
        Assert.assertEquals(60*60*1000, FlightService.ONE_HOUR);
        Assert.assertEquals(FlightService.TWO_HOURS, FlightService.ONE_HOUR*2);
    }
}
