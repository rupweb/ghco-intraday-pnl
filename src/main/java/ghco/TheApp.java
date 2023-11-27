package ghco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class TheApp {
    private static final Logger log = LogManager.getLogger(TheApp.class);
    public static void main(String[] args) {
        PnLCalculator calculator = new PnLCalculator();
        TradeRepository tradeRepo = new TradeRepository(calculator);

        // Find file
        if (args.length == 0) {
            System.out.println("Trades csv file not specified");
            System.out.println("To specify trades csv file add file to jar directory and restart with \\ filename as argument");
            System.out.println("For example: java -jar ghco-intraday-pnl.jar \"\\ghco_trades.csv\"");
        }
        else {
            System.out.println("Load trades file? y");
            Scanner scanner1 = new Scanner(System.in);
            String userInput = scanner1.next();

            if (userInput.equalsIgnoreCase("y")) {
                String csvFile = args[0];

                // Read file & make trade objects
                List<Trade> trades = new OpenCsv().populateTrades(csvFile);

                // Process the initial positions
                tradeRepo.updateTradeList(trades);
            }
        }

        // Implement a simple CLI for manual trades and cancellations
        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("Enter a trade (format: action, bbgCode, ccy, side, quantity, price)");
        System.out.println("For example (NEW, ABC1, USD, B, 100, 100.00)");
        System.out.println("Or enter a PnL criteria(format: PNL, criteria)");
        System.out.println("For example (PNL, Strategy2)");
        System.out.println("Or enter (PNL, LIST) to see the available criteria");
        System.out.println("Type 'EXIT' to quit:");
        System.out.println("Total PnL: " + calculator.calculateTotalPnL());

        do {
            System.out.println("Enter trade or PnL criteria:");
            System.out.println("For example (NEW, ABC1, USD, B, 100, 100.00)");
            System.out.println("Type 'EXIT' to quit:");
            userInput = scanner.nextLine();

            if (!"EXIT".equalsIgnoreCase(userInput)) {
                // Parse user input and create a Trade object
                if ("".equals(userInput)) {
                    System.out.println("Unknown input");
                    continue;
                }

                try {
                    if (getAction(userInput)) {
                        Trade userTrade = parseUserInput(userInput);

                        System.out.println();
                        System.out.println("Created trade with id: " + userTrade.getTradeId());

                        // Process the user's trade
                        tradeRepo.processTrade(userTrade);

                        // Calculate and display total PnL
                        double totalPnL = calculator.calculateCriteriaPnL(new String[]{userTrade.getBbgCode()});
                        System.out.println("Total PnL for bbgCode " + userTrade.getBbgCode() + ": " + totalPnL);
                        System.out.println("Total PnL: " + calculator.calculateTotalPnL());
                    }
                    else {
                        String criteria = getCriteria(userInput);
                        if (criteria.equals("LIST"))
                            listCriteria(calculator);
                        else
                            printPnL(calculator, criteria);
                    }
                } catch (Exception e) {
                    System.out.println("Could not parse input. Please try again.");
                    log.error("Could not parse input: {}", userInput, e);
                }
            }

        } while (!"EXIT".equalsIgnoreCase(userInput));
    }

    private static void listCriteria(PnLCalculator calculator) {
        calculator.listCriteria();
    }

    private static void printPnL(PnLCalculator calculator, String criteria) {
        double totalPnL = calculator.calculateCriteriaPnL(new String[]{criteria});
        System.out.println("Total PnL for criteria " + criteria + ": " + totalPnL);
    }

    private static String getCriteria(String input) {
        String[] parts = input.split("\\s*,\\s*");

        String action = parts[0];
        return parts[1];
    }

    private static boolean getAction(String input) {
        String[] parts = input.split("\\s*,\\s*");

        String action = parts[0];
        return !Objects.equals(action, "PNL");
    }

    private static Trade parseUserInput(String input) {
        // Implement logic to parse user input and create a Trade object
        String[] parts = input.split("\\s*,\\s*");

        String action = parts[0];
        String bbgCode = parts[1];
        String currency = parts[2];
        char side = parts[3].charAt(0);
        int quantity = Integer.parseInt(parts[4]);
        double price = Double.parseDouble(parts[5]);

        // Create and return a Trade object with the parsed values
        String id = UUID.randomUUID().toString().substring(0, 8);

        return new Trade(id, bbgCode, currency, side, price, quantity, "Portfolio" + bbgCode, action,
                "Account" + bbgCode, "Strategy" + bbgCode, "User" + bbgCode, LocalDateTime.now(), "NONE");
    }
}
