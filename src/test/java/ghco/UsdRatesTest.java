package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsdRatesTest {
    private static final Logger log = LogManager.getLogger(UsdRatesTest.class);

    @Test
    void testGBP() {
        log.info("In testGBP");

        // Make a non USD trade
        Trade trade = new Trade("82d06d8c858148448f284b44384659a6", " VOO US ETF", "GBP", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        PositionUpdate update = new PositionUpdate("test", trade);

        // Assert
        assertEquals(1745.0704117132002, update.getPrice());
    }

    @Test
    void testEUR() {
        log.info("In testEUR");
        // Make a non USD trade
        Trade trade = new Trade("82d06d8c858148448f284b44384659a6", " VOO US ETF", "EUR", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        PositionUpdate update = new PositionUpdate("test", trade);

        // Assert
        assertEquals(1518.3727872104, update.getPrice());
    }

    @Test
    void testNOK() {
        log.info("In testNOK");

        // Make a non USD trade
        Trade trade = new Trade("82d06d8c858148448f284b44384659a6", " VOO US ETF", "NOK", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        PositionUpdate update = new PositionUpdate("test", trade);

        // Assert
        assertEquals(129.3487980938934, update.getPrice());
    }

    @Test
    void testJPY() {
        log.info("In testJPY");

        // Make a non USD trade
        Trade trade = new Trade("82d06d8c858148448f284b44384659a6", " VOO US ETF", "JPY", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        PositionUpdate update = new PositionUpdate("test", trade);

        // Assert
        assertEquals(9.308098602941177, update.getPrice());
    }

    @Test
    void testKRW() {
        log.info("In testKRW");

        // Make a non USD trade
        Trade trade = new Trade("82d06d8c858148448f284b44384659a6", " VOO US ETF", "KRW", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        PositionUpdate update = new PositionUpdate("test", trade);

        // Assert
        assertEquals(1.0700032665073498, update.getPrice());
    }

    @Test
    void testUSD() {
        log.info("In testUSD");

        // Make a USD trade
        Trade trade = new Trade("82d06d8c858148448f284b44384659a6"," VOO US ETF","USD", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        PositionUpdate update = new PositionUpdate("test", trade);

        // Assert
        assertEquals(1392.491551, update.getPrice());
    }
}
