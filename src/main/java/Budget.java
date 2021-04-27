public class Budget {
    private final String yearMonth;
    public final int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public int getAmount() {
        return amount;
    }
}
