package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AveragePriceTest {
    private static final Logger log = LogManager.getLogger(AveragePriceTest.class);

    @Test
    void testAverages() {
        log.info("In testAverages");

        // Create prices
        double p1 = 100;
        double p2 = 101;
        double p3 = 102;
        double p4 = 103;

        PnLAccount pnl = new PnLAccount("test");

        pnl.buyPosition(100, p1);
        pnl.buyPosition(100, p2);
        pnl.buyPosition(100, p3);
        pnl.sellPosition(100, p4);

        pnl.calculatePnL();
        assertEquals(200, pnl.calculatePnL());
    }
}
