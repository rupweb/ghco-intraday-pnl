package ghco;

public class PnLAccount {

    private String key;
    private double pnL;
    private double totalSellsUsd;
    private double totalBuysUsd;
    private long totalSellSize;
    private long totalBuySize;

    public PnLAccount(String key) {
        this.key = key;
    }

    public PnLAccount() {}

    public String getKey() { return key; }
    public double getPnL() { return pnL; }

    public void buyPosition(int size, double price) {
        double totalBuy = size * price;
        totalBuysUsd += totalBuy;
        totalBuySize += size;
    }

    public void sellPosition(int size, double price) {
        double totalSell = size * price;
        totalSellsUsd += totalSell;
        totalSellSize += size;
    }

    public double calculatePnL() {

        double averageSellPrice = totalSellsUsd / totalSellSize;
        double averageBuyPrice = totalBuysUsd / totalBuySize;
        double sellsLessBuys = totalSellsUsd - totalBuysUsd;

        long balance = totalBuySize - totalSellSize;
        double balanceCost;

        if (balance > 0)
            balanceCost = balance * averageBuyPrice;
        else
            balanceCost = balance * averageSellPrice;

        pnL = balanceCost + sellsLessBuys;
        return pnL;
    }
}
