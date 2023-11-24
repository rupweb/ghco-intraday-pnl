package ghco;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class OpenCsv {

    private static final Logger log = LogManager.getLogger(OpenCsv.class);

    public List<Trade> populateTrades(String csvFilePath) {

        log.info("In populateTrades with csvFilePath {}", csvFilePath);
        String workingDirectory = System.getProperty("user.dir");
        log.info("Working Directory {}", workingDirectory);
        String filePath = workingDirectory + csvFilePath;
        log.info("Opening file {}", filePath);

        List<Trade> trades = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            // Skip the first line (header)
            bufferedReader.readLine();

            try (CSVReader csvReader = new CSVReader(bufferedReader)) {
                List<String[]> records = csvReader.readAll();

                for (String[] record : records) {
                    // Assuming order of columns
                    String tradeId = record[0];
                    String bbgCode = record[1];
                    String currency = record[2];
                    char side = record[3].charAt(0);
                    double price = Double.parseDouble(record[4]);
                    int volume = Integer.parseInt(record[5]);
                    String portfolio = record[6];
                    String action = record[7];
                    String account = record[8];
                    String strategy = record[9];
                    String user = record[10];
                    LocalDateTime tradeTimeUTC = getTradeTime(record[11], formatter);
                    String valueDate = record[12];

                    // Create Trade object
                    Trade trade = new Trade(tradeId, bbgCode, currency, side, price, volume, portfolio, action,
                                            account, strategy, user, tradeTimeUTC, valueDate);

                    trades.add(trade);
                }

            } catch (CsvException e) {
                log.error("Can't transform CSV file", e);
            }

        } catch (IOException e) {
            log.error("Can't open CSV file", e);
        }

        return trades;
    }

    private LocalDateTime getTradeTime(String s, DateTimeFormatter formatter) {
        LocalDateTime tradeTimeUTC = LocalDateTime.now();

        try {
            tradeTimeUTC = LocalDateTime.parse(s, formatter);
        } catch (DateTimeParseException dtpe) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss.SSSSSS");
            tradeTimeUTC = LocalDateTime.parse(s, formatter2);
        }

        return tradeTimeUTC;
    }
}
