package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionsTest {
    private static final Logger log = LogManager.getLogger(PositionsTest.class);

    @Test
    void testPnLCalculator() {
        log.info("In testPnLCalculator");
        PnLCalculator calculator = new PnLCalculator();

        // Create sample positions for testing
        PositionUpdate position1 = new PositionUpdate("ABC2", LocalDateTime.parse("2010-01-01T09:25:35.123456"), 'B', 50, 200);
        PositionUpdate position2 = new PositionUpdate("ABC2", LocalDateTime.parse("2010-01-01T09:25:36.123456"), 'B', 50, 202);
        PositionUpdate position3 = new PositionUpdate("ABC2", LocalDateTime.parse("2010-01-01T09:25:37.123456"), 'S', 100, 205);

        // Process the positions
        calculator.testPositionUpdate(position1);
        calculator.testPositionUpdate(position2);
        calculator.testPositionUpdate(position3);

        // Calculate and check PnL for each BBGCode and total PnL
        double pnlABC2 = calculator.getPosition("ABC2").calculatePnL();
        double totalPnL = calculator.calculateCriteriaPnL(new String[]{ "ABC2"});

        // PnL calculation in example
        assertEquals(400, pnlABC2, 0.001);
        assertEquals(400, totalPnL, 0.001);
    }
}
