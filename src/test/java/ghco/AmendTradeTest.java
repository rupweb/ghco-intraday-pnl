package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmendTradeTest {
    private static final Logger log = LogManager.getLogger(AmendTradeTest.class);

    @Test
    void testAmendTrade() {
        log.info("In testAmendTrade");

        PnLCalculator calculator = new PnLCalculator();
        TradeRepository tradeRepo = new TradeRepository(calculator);

        // Make a trade
        Trade trade1 = new Trade("82d06d8c858148448f284b44384659a6"," VOO US ETF","GBP", 'S', 1392.491551, 751174,
                "portfolio2", "NEW", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        // Process
        tradeRepo.processTrade(trade1);

        // Get the PnL
        calculator.getPosition("VOO US ETF");

        // Amend
        Trade trade2 = new Trade("82d06d8c858148448f284b44384659a6"," VOO US ETF","USD", 'S', 1392.491551, 751174,
                "portfolio2", "AMEND", "Account5", "Strategy1", "User3", LocalDateTime.parse("2010-12-09T16:06:10.133460"), "20101209");

        // Process
        tradeRepo.processTrade(trade1);

        // Get the PnL
        calculator.getPosition("VOO US ETF");
    }
}
