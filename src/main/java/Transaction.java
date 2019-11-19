import java.math.BigDecimal;
import java.time.LocalDate;
public class Transaction {
    private BigDecimal amount;
    private String contractor;
    private LocalDate date;
    private Direction direction;
    private String description;
    public Transaction (BigDecimal amount, String contractor, LocalDate date, Direction direction, String description)
    {
        this.amount = amount;
        this.contractor = contractor;
        this.date = date;
        this.direction = direction;
        this.description = description;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public Direction getDirection() {
        return direction;
    }
    public String getContractor() {
        return contractor;
    }
    public String getDescription() {
        return description;
    }


}
