package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PnLCalculator {
    private static final Logger log = LogManager.getLogger(PnLCalculator.class);
    private final Map<String, PnLAccount> positions = new HashMap<>();
    public PnLAccount getPosition(String key) { return positions.get(key); }

    void newPosition(Trade newTrade) {
        // Create unique identifiers for each category
        String bbgCodeKey = newTrade.getBbgCode();
        String portfolioKey = newTrade.getPortfolio();
        String strategyKey = newTrade.getStrategy();
        String userKey = newTrade.getUser();
        String currencyKey = newTrade.getCurrency();

        // Update positions for each category
        updatePositionWithTrade(bbgCodeKey, newTrade);
        updatePositionWithTrade(portfolioKey, newTrade);
        updatePositionWithTrade(strategyKey, newTrade);
        updatePositionWithTrade(userKey, newTrade);
        updatePositionWithTrade(currencyKey, newTrade);
    }

    private void updatePositionWithTrade(String key, Trade trade) {
        // Get or create the position for the given key
        PnLAccount pnLAccount = positions.computeIfAbsent(key, value -> new PnLAccount(key));

        // Update the position
        updatePositionWithTradeComponents(pnLAccount, trade);

        // Put the updated position back into the map
        positions.put(key, pnLAccount);
    }

    private void updatePositionWithTradeComponents(PnLAccount pnLAccount, Trade trade) {
        // Logic to calculate PnL for a single trade
        PositionUpdate update = new PositionUpdate(pnLAccount.getKey(), trade);
        calculateNewPosition(pnLAccount, update);
    }

    private void calculateNewPosition(PnLAccount pnLAccount, PositionUpdate update) {
        switch (update.getSide()) {
            case 'B':
                // Add position
                pnLAccount.buyPosition(update.getQuantity(), update.getPrice());
                break;

            case 'S':
                // Subtract position
                pnLAccount.sellPosition(update.getQuantity(), update.getPrice());
                break;

            default:
                log.error("Unknown side: {}", update.getSide());
        }
    }

    public double calculateTotalPnL() {
        double totalPnL = 0;

        for (PnLAccount pnlAccount : positions.values()) {
            double PnL = pnlAccount.calculatePnL();
            System.out.println("PnL for criteria: " + pnlAccount.getKey() + " is " + PnL);
            totalPnL += PnL;
            System.out.println("Running total PnL: " + totalPnL);
        }

        return totalPnL;
    }

    public double calculateCriteriaPnL(String[] keys) {
        double totalPnL = 0;

        for (String key : keys) {
            if (positions.containsKey(key)) {
                totalPnL += positions.get(key).calculatePnL();
            }
        }

        return totalPnL;
    }

    public void testPositionUpdate(PositionUpdate update) {
        // Get or create the position for the given key
        PnLAccount pnLAccount = positions.computeIfAbsent(update.getKey(), value -> new PnLAccount(update.getKey()));

        // Update the position
        updatePosition(pnLAccount, update);

        // Put the updated position back into the map
        positions.put(update.getKey(), pnLAccount);
    }

    private void updatePosition(PnLAccount pnLAccount, PositionUpdate update) {
        // Update PnL for this position with new trade
        calculateNewPosition(pnLAccount, update);
    }

    public void cancelPosition(Trade cancelTrade) {
        // Create unique identifiers for each category
        String bbgCodeKey = cancelTrade.getBbgCode();
        String portfolioKey = cancelTrade.getPortfolio();
        String strategyKey = cancelTrade.getStrategy();
        String userKey = cancelTrade.getUser();
        String currencyKey = cancelTrade.getCurrency();

        // Change side
        Trade reverseTrade = reverseTrade(cancelTrade);

        // Update positions for each category
        updatePositionWithTrade(bbgCodeKey, reverseTrade);
        updatePositionWithTrade(portfolioKey, reverseTrade);
        updatePositionWithTrade(strategyKey, reverseTrade);
        updatePositionWithTrade(userKey, reverseTrade);
        updatePositionWithTrade(currencyKey, reverseTrade);
    }

    private Trade reverseTrade(Trade t) {

        if (t.getSide() == 'B')
            return new Trade(t.getTradeId(), t.getBbgCode(), t.getCurrency(), 'S', t.getPrice(), t.getVolume(), t.getPortfolio(),
                t.getAction(), t.getAccount(), t.getStrategy(), t.getUser(), t.getTradeTimeUTC(), t.getValueDate());
        else
            return new Trade(t.getTradeId(), t.getBbgCode(), t.getCurrency(), 'B', t.getPrice(), t.getVolume(), t.getPortfolio(),
                t.getAction(), t.getAccount(), t.getStrategy(), t.getUser(), t.getTradeTimeUTC(), t.getValueDate());
    }

    public void listCriteria() {
        positions.keySet().forEach(System.out::println);
    }
}
