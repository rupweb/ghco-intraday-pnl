package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradesPnLTest {

    private static final Logger log = LogManager.getLogger(TradesPnLTest.class);

    @Test
    void testPnLCalculator() {
        log.info("In testPnLCalculator");
        PnLCalculator calculator = new PnLCalculator();
        TradeRepository tradeRepo = new TradeRepository(calculator);

        // Find file
        String csvFile = "/src/test/resources/ghco_positions_test.csv";

        // Read file & make trade objects
        List<Trade> trades = new OpenCsv().populateTrades(csvFile);

        // Process the positions
        tradeRepo.updateTradeList(trades);

        // Calculate and check PnL for each BBGCode and total PnL
        double pnlABC1 = calculator.getPosition("ABC1").calculatePnL();
        double pnlABC2 = calculator.getPosition("ABC2").calculatePnL();
        double totalPnL = calculator.calculateCriteriaPnL(new String[]{ "ABC1", "ABC2"});

        // Assuming the PnL calculation is as described in the example
        assertEquals(-50, pnlABC1, 0.001);
        assertEquals(400, pnlABC2, 0.001);
        assertEquals(350, totalPnL, 0.001);
    }
}
