import java.util.regex.Pattern;
public class RegexBuilder {
    static final String SOME_TEXT = "^.+,\\d+,\\D{3}";
    static final String DATE = "(?<date>\\d{2}\\.\\d{2}\\.\\d{2})";
    static final String SOME_TEXT2 = ".{10}";
    static final String CONTRACTOR = ".{16}(?<contractor>.+)(\\d{2}\\.\\d{2}\\.\\d{2} \\d{2}\\.\\d{2}\\.\\d{2}).+";
    static final String INCOME = "(?<income>\\d+)";
    static final String EXPENSE = "(?<expense>.+)$";

    static Pattern build() {
        return Pattern.compile(
                String.join(",",
                        SOME_TEXT,
                        DATE,
                        SOME_TEXT2,
                        CONTRACTOR,
                        INCOME,
                        EXPENSE
                ));
    }
}
