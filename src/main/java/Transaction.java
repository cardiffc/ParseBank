import java.math.BigDecimal;
import java.time.LocalDate;
public class Transaction {
    private BigDecimal amount;
    private String contractor;
    private LocalDate date;
    private Direction direction;
    private String description;
    protected Transaction(BigDecimal amount, String contractor, LocalDate date, Direction direction)
    {
        this.amount = amount;
        this.contractor = contractor;
        this.date = date;
        this.direction = direction;
    }
    protected BigDecimal getAmount() {
        return amount;
    }
    protected LocalDate getDate() {
        return date;
    }
    protected Direction getDirection() {
        return direction;
    }
    protected String getContractor() {
        return contractor;
    }


}
