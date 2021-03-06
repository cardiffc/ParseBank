import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private String file;
    private static final Logger MARKLOGGER = LogManager.getLogger(Main.class);
    private static final Marker NO_MATCHES_MARKER = MarkerManager.getMarker("NO_MATCHES");
    protected Parser(String file) {
        this.file = file;
    }

    protected List<Transaction> parseMovements() throws IOException {
        boolean matchFound = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        List<Transaction> allTransactions = new ArrayList<>();
        Pattern pattern = RegexBuilder.build();
        List<String> linesFromFile = Files.readAllLines(Paths.get(file));
        for (String line : linesFromFile)
        {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
            {
                matchFound = true;
                String preContractor = matcher.group("contractor").replaceAll("\\\\","/").trim();
                String contractor = preContractor.substring(preContractor.lastIndexOf("/") + 1);
                Long.parseLong(matcher.group("income"));
                Direction direction = (Long.parseLong(matcher.group("income")) > 0) ? Direction.INCOME
                                                                                          : Direction.EXPENSE;
                LocalDate date = LocalDate.parse(matcher.group("date"),formatter);
                BigDecimal amount = new BigDecimal((direction.equals(Direction.INCOME)) ? matcher.group("income")
                        : matcher.group("expense").replaceAll("\"","").replaceAll(",","."));
                allTransactions.add(new Transaction(amount, contractor, date, direction));
            }
        }
        if (!matchFound)
        {
            MARKLOGGER.info(NO_MATCHES_MARKER,"Файл путой или не верный формат файла!");
        }
        return allTransactions;
    }
}
