import java.time.LocalDate;
public class Transaction {
    private long amount;
    private String contractor;
    private LocalDate date;
    private Direction direction;
    private String description;
    public Transaction (long amount, String contractor, LocalDate date, Direction direction, String description)
    {
        this.amount = amount;
        this.contractor = contractor;
        this.date = date;
        this.direction = direction;
        this.description = description;
    }
    public long getAmount() {
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
