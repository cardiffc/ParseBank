import java.time.LocalDate;

public class Main {
    private static String movementsFile = "data/MovementList.csv";
    public static void main(String[] args) {
        long amount = 1003000;
        String contractor = "testcontractor";
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOME;
        String description = "test description";

        Transaction first = new Transaction(amount,contractor,date,direction,description);





    }
}
