package ghco;

import java.time.LocalDateTime;
import java.util.Objects;

public class Trade {
    private final String tradeId;
    private final String bbgCode;
    private final String currency;
    private final char side;
    private final double price;
    private final int volume;
    private final String portfolio;
    private final String action;
    private final String account;
    private final String strategy;
    private final String user;
    private final LocalDateTime tradeTimeUTC;
    private final String valueDate;

    public String getTradeId() { return tradeId; }
    public String getBbgCode() { return bbgCode; }
    public String getCurrency() { return currency; }
    public char getSide() { return side; }
    public double getPrice() { return price; }
    public int getVolume() { return volume; }
    public String getPortfolio() { return portfolio; }
    public String getAction() { return action; }
    public String getAccount() { return account; }
    public String getStrategy() { return strategy; }
    public String getUser() { return user; }
    public LocalDateTime getTradeTimeUTC() { return tradeTimeUTC; }
    public String getValueDate() { return valueDate; }

    public Trade(String tradeId, String bbgCode, String currency, char side, double price, int volume, String portfolio,
                 String action, String account, String strategy, String user, LocalDateTime tradeTimeUTC, String valueDate) {
        this.tradeId = tradeId;
        this.bbgCode = bbgCode;
        this.currency = currency;
        this.side = side;
        this.price = price;
        this.volume = volume;
        this.portfolio = portfolio;
        this.action = action;
        this.account = account;
        this.strategy = strategy;
        this.user = user;
        this.tradeTimeUTC = tradeTimeUTC;
        this.valueDate = valueDate;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", bbgCode='" + bbgCode + '\'' +
                ", currency='" + currency + '\'' +
                ", side=" + side +
                ", price=" + price +
                ", volume=" + volume +
                ", portfolio='" + portfolio + '\'' +
                ", action='" + action + '\'' +
                ", account='" + account + '\'' +
                ", strategy='" + strategy + '\'' +
                ", user='" + user + '\'' +
                ", tradeTimeUTC='" + tradeTimeUTC + '\'' +
                ", valueDate='" + valueDate + '\'' +
                '}';
    }

    // Requires unique trade id operation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(tradeId, trade.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId);
    }
}
