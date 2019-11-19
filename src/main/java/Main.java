import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String movementsFile = "data/movementList.csv";
    public static void main(String[] args) throws IOException {
        List<Transaction> allTransactions = new ArrayList<>();
        long amount;
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        CSVReader parseFile = new CSVReader(new FileReader(movementsFile), ',', '"', '~', 1);
        List<String[]> parsedLines = parseFile.readAll();
        for (String[] element : parsedLines)
        {
            Direction direction = (Long.parseLong(element[6]) > 0) ? Direction.INCOME : Direction.EXPENSE;
            if (Long.parseLong(element[6]) > 0) {
                double doubleAmount = Double.parseDouble(element[6]);
                amount = (long) doubleAmount * 100;
            } else {
                double doubleAmount = Double.parseDouble(element[7].replaceAll(",","."));
                amount = (long) doubleAmount * 100;
            }
            date = LocalDate.parse(element[3],formatter);
            String preContractor = element[5].substring(element[5].indexOf("   ")
                    , element[5].lastIndexOf("          ")).trim();
            String contractor = preContractor.substring(preContractor.replaceAll("\\\\","/")
                    .lastIndexOf("/") + 1).trim();
            String description = element[5];
            allTransactions.add(new Transaction(amount, contractor, date, direction, description));
        }
        allTransactions.stream().filter(element -> element.getDirection().equals(Direction.EXPENSE))
                .forEach(element -> System.out.println(element.getContractor() + "/" + element.getAmount() / 100));
    }
}
