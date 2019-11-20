import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static final String movementsFile = "data/movementList.csv";
    public static void main(String[] args) throws IOException {
        Parser movementParser = new Parser(movementsFile);
        List<Transaction> allTransactions = movementParser.parseMovements();
        System.out.println("Total income = " + getTotalIncome(allTransactions) + "\n" +
                "Total expense = " + getTotalExpense(allTransactions));
        System.out.println("Expense by contractor:");
        listExpanseByContractor(allTransactions);

    }
    private static void listExpanseByContractor(List<Transaction> transactions)
    {
        TreeMap<String, BigDecimal> expanseByContractor = new TreeMap();
        transactions.stream().filter(transaction -> transaction.getDirection().equals(Direction.EXPENSE))
                .forEach(transaction -> expanseByContractor.put(transaction.getContractor(), BigDecimal.ZERO));
        transactions.stream().filter(transaction -> transaction.getDirection().equals(Direction.EXPENSE))
                .forEach(transaction -> {
                    for (Map.Entry<String, BigDecimal> contr : expanseByContractor.entrySet()) {
                        contr.setValue(contr.getKey().equals(transaction.getContractor())
                                ? contr.getValue().add(transaction.getAmount()) : contr.getValue());
                    }
                });
        for (Map.Entry<String,BigDecimal> entry : expanseByContractor.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    private static BigDecimal getTotalIncome(List<Transaction> transactions)
    {
        BigDecimal totalIncome = BigDecimal.ZERO;
        for (Transaction trans : transactions) {
            totalIncome = (trans.getDirection().equals(Direction.INCOME)) ? totalIncome.add(trans.getAmount())
                    : totalIncome;
        }
        return totalIncome;
    }
    private static BigDecimal getTotalExpense(List<Transaction> transactions)
    {
        BigDecimal totalExpense = BigDecimal.ZERO;
        for (Transaction trans : transactions) {
            totalExpense = (trans.getDirection().equals(Direction.EXPENSE)) ? totalExpense.add(trans.getAmount())
                    : totalExpense;
        }
        return totalExpense;
    }
}
