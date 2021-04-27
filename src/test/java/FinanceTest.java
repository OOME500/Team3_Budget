import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinanceTest {

    private FakeRepo repo;
    private Finance finance;

    private class FakeRepo implements BudgetRepo {


        private List<Budget> budgets;

        @Override
        public List<Budget> getAll() {
            return budgets;
        }

        public void setBudgets(List<Budget> budgets) {
            this.budgets = budgets;
        }
    }

    @BeforeEach
    void setUp() {
        repo = new FakeRepo();
        finance = new Finance(repo);
    }

    @Test
    void query_whole_month() {
        repo.setBudgets(Arrays.asList(new Budget("202101", 31)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 1, 1)
                , LocalDate.of(2021, 1, 31));

        assertEquals(31, queryBudget);
    }


    @Test
    void query_whole_month_no_data() {
        repo.setBudgets(Arrays.asList(new Budget("202102", 31)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 1, 1)
                , LocalDate.of(2021, 1, 31));

        assertEquals(0, queryBudget);
    }

    @Test
    void query_two_whole_month(){
        repo.setBudgets(Arrays.asList(new Budget("202101", 31),new Budget("202102",28)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 1, 1)
                , LocalDate.of(2021, 2, 28));
        assertEquals(59, queryBudget);
    }

    @Test
    void query_part_month(){
        repo.setBudgets(Arrays.asList(new Budget("202101", 31),new Budget("202102",28)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 1, 1)
                , LocalDate.of(2021, 1, 10));
        assertEquals(10, queryBudget);
    }


    @Test
    void query_part_month_no_data(){
        repo.setBudgets(Arrays.asList(new Budget("202101", 31),new Budget("202102",28)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 3, 1)
                , LocalDate.of(2021, 4, 30));
        assertEquals(0, queryBudget);
    }

    @Test
    void query_part_two_month_data(){
        repo.setBudgets(Arrays.asList(new Budget("202101", 31),new Budget("202102",28)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 1, 31)
                , LocalDate.of(2021, 2, 10));
        assertEquals(11, queryBudget);
    }

    @Test
    void query_invalid_date(){
        repo.setBudgets(Arrays.asList(new Budget("202101", 31),new Budget("202102",28)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 2, 28)
                , LocalDate.of(2021, 1, 1));
        assertEquals(0, queryBudget);
    }

    @Test
    void query_part_two_month_with_whole_month(){
        repo.setBudgets(Arrays.asList(
                new Budget("202101", 31),
                new Budget("202102",28),
                new Budget("202103",31)));
        double queryBudget = finance.queryBudget(LocalDate.of(2021, 1, 31)
                , LocalDate.of(2021, 3, 1));
        assertEquals(30, queryBudget);
    }

}