package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalPnLTest {
    private static final Logger log = LogManager.getLogger(TotalPnLTest.class);

    @Test
    void testAmendTrade() {
        log.info("In testAmendTrade");

        PnLCalculator calculator = new PnLCalculator();
        TradeRepository tradeRepo = new TradeRepository(calculator);

        // Buy
        Trade trade1 = new Trade("82d06d8c858148448f284b44384659a6","TEST1","USD", 'B', 100, 100,
                "portfolio1", "NEW", "Account1", "Strategy1", "User1", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        // Process
        tradeRepo.processTrade(trade1);

        // Get the PnL
        calculator.getPosition("TEST1");
        assertEquals(0, calculator.getPosition("TEST1").getPnL());

        // Sell
        Trade trade2 = new Trade("82d06d8c858148448f284b44384659a7","TEST1","USD", 'S', 99, 100,
                "portfolio2", "NEW", "Account2", "Strategy2", "User2", LocalDateTime.parse("2010-12-09T16:06:10.133461"), "20101209");

        // Process
        tradeRepo.processTrade(trade2);

        // Get the PnL
        calculator.getPosition("TEST1");
        double totalPnL = calculator.calculateTotalPnL();
        assertEquals(-100, calculator.getPosition("TEST1").getPnL());
        assertEquals(-100, totalPnL); // Ensure PnL not double counted
    }
}
