package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

// A USD representation of the update
public class PositionUpdate {
    private static final Logger log = LogManager.getLogger(PositionUpdate.class);

    private final String key;
    private final LocalDateTime time;
    private final char side;
    private final int quantity;
    private final double price;

    private CurrencyRates rates = new CurrencyRates();

    public String getKey() { return key; }
    public char getSide() { return side; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public PositionUpdate(String key, LocalDateTime time, char side, int quantity, double price) {
        this.key = key;
        this.time = time;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
    }

    public PositionUpdate(PositionUpdate position) {
        this.key = position.key;
        this.time = position.time;
        this.side = position.side;
        this.quantity = position.quantity;
        this.price = position.price;
    }

    public PositionUpdate(String key, Trade trade) {
        this.key = key;
        this.time = trade.getTradeTimeUTC();
        this.side = trade.getSide();
        this.quantity = trade.getVolume();

        if (trade.getCurrency().equals("USD"))
            this.price = trade.getPrice();
        else
            this.price = getUsdPrice(trade.getCurrency(), trade.getPrice());
    }

    public double getUsdPrice(String currency, double price) {
        switch (currency) {
            case "GBP":
                return price * rates.GBPUSD;
            case "EUR":
                return price * rates.EURUSD;
            case "NOK":
                return price / rates.USDNOK;
            case "JPY":
                return price / rates.USDJPY;
            case "KRW":
                return price / rates.USDKRW;
            default: {
                System.out.println("Can't convert currency: " + currency);
                log.error("Can't convert currency: {}", currency);
                return price;
            }
        }

    }
}
