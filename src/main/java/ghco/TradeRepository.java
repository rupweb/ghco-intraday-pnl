package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeRepository {
    private static final Logger log = LogManager.getLogger(TradeRepository.class);

    private final PnLCalculator calculator;
    private final Map<String, Trade> trades = new HashMap<>();

    private int count = 1;

    TradeRepository(PnLCalculator calculator) {
        this.calculator = calculator;
    }

    public void processTrade(Trade newTrade) {
        log.info("In processTrade {} with id: {}", count, newTrade.getTradeId());
        count++;

        switch(newTrade.getAction()) {
            case "NEW":
                addTrade(newTrade);
                break;
            case "AMEND":
                amendTrade(newTrade);
                break;
            case "CANCEL":
                cancelTrade(newTrade);
                break;
            default:
                System.out.println("Unknown trade operation: " + newTrade.getAction());
                log.error("Unknown trade operation: {}", newTrade.getAction());
        }
    }

    public void updateTradeList(List<Trade> newTrades) {
        log.info("In updateTradeList");

        for (Trade newTrade : newTrades)
            processTrade(newTrade);
    }

    public void addTrade(Trade newTrade) {
        trades.put(newTrade.getTradeId(), newTrade);

        // Work out PnL on this new trade
        calculator.newPosition(newTrade);
    }

    public void amendTrade(Trade amendTrade) {
        Trade existingTrade = trades.get(amendTrade.getTradeId());

        if (existingTrade != null) {
            // Remove the existing trade
            trades.remove(existingTrade.getTradeId());

            // Add the amended trade
            trades.put(amendTrade.getTradeId(), amendTrade);

            // For PnL cancel earlier trade and book new
            calculator.cancelPosition(existingTrade);
            calculator.newPosition(amendTrade);
        } else {
            // Handle case when trade isn't found
            System.out.println("Trade not found: " + amendTrade);
            log.error("Trade not found: {}", amendTrade);
        }
    }

    public void cancelTrade(Trade cancelTrade) {
        try {
            trades.remove(cancelTrade.getTradeId());

            // Work out PnL on the cancelled trade
            calculator.cancelPosition(cancelTrade);
        } catch (Exception e) {
            System.out.println("Can't cancel trade: " + cancelTrade);
            log.error("Can't cancel trade: {}", cancelTrade);
        }
    }
}
