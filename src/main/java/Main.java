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
        System.out.println("Total income = " + getTotalIncome(allTransactions) + "\n" + "Total expense = " + getTotalExpense(allTransactions));
        System.out.println("Expense by contractor:");
        listExpanseByContractor(allTransactions);

    }
    private static void listExpanseByContractor(List<Transaction> transactions)
    {
        TreeMap<String, BigDecimal> expanseByContractor = new TreeMap();
        transactions.stream().filter(el -> el.getDirection().equals(Direction.EXPENSE))
                .forEach(transaction -> expanseByContractor.put(transaction.getContractor(), BigDecimal.ZERO));
        for (Transaction trans : transactions)
        {
            if (trans.getDirection().equals(Direction.EXPENSE)) {
                for (Map.Entry<String, BigDecimal> contr : expanseByContractor.entrySet()) {
                    contr.setValue(contr.getKey().equals(trans.getContractor()) ? contr.getValue().add(trans.getAmount())
                                                                                : contr.getValue());
                }
            }
        }
        for (Map.Entry<String,BigDecimal> entry : expanseByContractor.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    private static BigDecimal getTotalIncome(List<Transaction> transactions)
    {
        BigDecimal totalIncome = BigDecimal.ZERO;
//        BigDecimal totalI = new BigDecimal.ZERO;
//        transactions.stream().filter(transaction -> transaction.getDirection().equals(Direction.INCOME))
//                .collect(to)
//                .forEach(outTransaction -> totalIncome.add(outTransaction.getAmount()));
        for (Transaction trans : transactions)
        {
            totalIncome = (trans.getDirection().equals(Direction.INCOME)) ? totalIncome.add(trans.getAmount())
                                                                          : totalIncome;
        }
        return totalIncome;
    }
    private static BigDecimal getTotalExpense(List<Transaction> transactions)
    {
        BigDecimal totalExpense = BigDecimal.ZERO;
        for (Transaction trans : transactions)
        {
            totalExpense = (trans.getDirection().equals(Direction.EXPENSE)) ? totalExpense.add(trans.getAmount())
                                                                            : totalExpense;
        }
        return totalExpense;
    }
}
