package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReadCSVTest {

    private static final Logger log = LogManager.getLogger(ReadCSVTest.class);

    @Test
    void readTest() {
        log.info("In readTest");

        // Find file
        String csvFile = "/src/test/resources/ghco_trades_test.csv";

        // Read file & make trade objects
        List<Trade> trades = new OpenCsv().populateTrades(csvFile);

        // Assert
        assertEquals(9, trades.size());

        log.info("Out readTest");
    }
}
