import java.util.regex.Pattern;

public class StringParser {
    private static final Pattern REGEX_DESCR = DescriptionRegexBuilder.build();

    private static class DescriptionRegexBuilder {
        static final String CREDIT_NUMBER = "[\\d+]+";
        static final String SOME_CODE = "[\\d]+";
        static final String CONTRACTOR = "(?<contractor>.+?)";
        static final String DATE = "\\d{2}\\.\\d{2}\\.\\d{2}";
        static final String AMOUNT = "[\\d.]+";
        static final String CURRENCY = "[\\d.]+";
        static final String MCC = ".+";

        static Pattern build() {
            return Pattern.compile(
                    String.join("\\s+",
                            CREDIT_NUMBER,
                            SOME_CODE,
                            CONTRACTOR,
                            DATE,
                            DATE,
                            AMOUNT,
                            CURRENCY,
                            MCC
                    ));
        }
    }
}

