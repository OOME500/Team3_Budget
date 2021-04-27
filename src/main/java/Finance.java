import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Finance {


    private BudgetRepo budgetRepo;

    public Finance(BudgetRepo budgetRepo) {

        this.budgetRepo = budgetRepo;
    }


    public double queryBudget(LocalDate start, LocalDate end) {
        double result = 0;

        if (start.isAfter(end)) {
            return 0;
        }

        for (Budget budget : budgetRepo.getAll()) {
            YearMonth yearMonth = YearMonth.parse(budget.getYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));
            YearMonth startMonth = YearMonth.from(start);

            YearMonth endMonth = YearMonth.from(end);

            if (yearMonth.equals(startMonth) && yearMonth.equals(endMonth)) {
                int days = Period.between(start, end).getDays() + 1;
                int monthDays = yearMonth.lengthOfMonth();
                double ratio = days / (double) monthDays;
                result += budget.getAmount() * ratio;
            } else if (yearMonth.equals(startMonth)) {
                int days = Period.between(start, startMonth.atEndOfMonth()).getDays() + 1;
                int monthDays = yearMonth.lengthOfMonth();
                double ratio = days / (double) monthDays;
                result += budget.getAmount() * ratio;
            } else if (yearMonth.equals(endMonth)) {
                int days = Period.between(endMonth.atDay(1), end).getDays() + 1;
                int monthDays = yearMonth.lengthOfMonth();
                double ratio = days / (double) monthDays;
                result += budget.getAmount() * ratio;
            } else if (yearMonth.isAfter(startMonth) && yearMonth.isBefore(endMonth)) {
                result += budget.getAmount();
            }


        }
        //return budgetRepo.getAll().get(0).amount;
        return result;


    }
}
