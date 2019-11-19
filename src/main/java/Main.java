import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static final String movementsFile = "data/movementList.csv";
    public static void main(String[] args) throws IOException {
        List<Transaction> allTransactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        CSVReader parseFile = new CSVReader(new FileReader(movementsFile), ',', '"', '~', 1);

        List<String[]> parsedLines = parseFile.readAll();
        for (String[] element : parsedLines)
        {
            Direction direction = (Long.parseLong(element[6]) > 0) ? Direction.INCOME : Direction.EXPENSE;
            BigDecimal amount = new BigDecimal((Long.parseLong(element[6]) > 0) ? element[6] : element[7].replaceAll(",","."));
            LocalDate date = LocalDate.parse(element[3],formatter);

            String preContractor = element[5].substring(element[5].indexOf("   ")
                    , element[5].lastIndexOf("          ")).trim();

            String contractor = preContractor.substring(preContractor.replaceAll("\\\\","/")
                    .lastIndexOf("/") + 1).trim();

            String description = element[5].trim();
            allTransactions.add(new Transaction(amount, contractor, date, direction, description));
        }
        listExpanseByContractor(allTransactions);
    }
    private static void listExpanseByContractor(List<Transaction> transactions)
    {
        TreeMap<String, BigDecimal> expanseByContractor = new TreeMap();
        transactions.forEach(transaction -> expanseByContractor.put(transaction.getContractor(), new BigDecimal("0")));
        for (Transaction trans : transactions)
        {
            for (Map.Entry<String, BigDecimal> contr : expanseByContractor.entrySet()) {
             contr.setValue(contr.getKey().equals(trans.getContractor()) ? contr.getValue().add(trans.getAmount()) : contr.getValue());
            }
        }
        for (Map.Entry<String,BigDecimal> entry : expanseByContractor.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}
