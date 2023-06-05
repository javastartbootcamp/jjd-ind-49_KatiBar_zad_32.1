package pl.javastart.streamstask;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ExampleTest {

    @BeforeEach
    public List<User> takeUserList() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "Alicja", 20));
        users.add(new User(2L, "Dominik", 15));
        users.add(new User(3L, "Patrycja", 25));
        users.add(new User(4L, "Marcin", 30));
        users.add(new User(5L, "Tomek", 18));
        users.add(new User(6L, "Damian", 26));

        return users;
    }


    @BeforeEach
    public List<Expense> takeExpenseList() {
        List<Expense> expenses = new ArrayList<>();

        expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
        expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
        expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Skarpetki", new BigDecimal("39"), ExpenseType.WEAR));
        expenses.add(new Expense(2L, "Pizza", new BigDecimal("25"), ExpenseType.FOOD));

        return expenses;
    }



    @Test
    public void shouldReturnUsersWithEndsA() {
        // given
        StreamsTask streamsTask = new StreamsTask();
        List<User> users = takeUserList();

        // when
        Collection<User> women = streamsTask.findWomen(users);

        // then
        assertThat(women).containsAll(List.of(users.get(0), users.get(2)));
    }

    @Test
    public void shouldReturnExpensesGroupForUserId1L() {
        // given
        StreamsTask streamsTask = new StreamsTask();
        List<User> users = takeUserList();
        List<Expense> expenses = takeExpenseList();

        // when
        Map<Long, List<Expense>> groupExpensesByUserId = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        checkExpensesForUserId(groupExpensesByUserId, 1L, expenses);
    }

    @Test
    public void shouldReturnExpensesGroupForUserId2L() {
        // given
        StreamsTask streamsTask = new StreamsTask();
        List<User> users = takeUserList();
        List<Expense> expenses = takeExpenseList();

        // when
        Map<Long, List<Expense>> groupExpensesByUserId = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        checkExpensesForUserId(groupExpensesByUserId, 2L, expenses);
    }

    private static void checkExpensesForUserId(Map<Long, List<Expense>> groupExpensesByUserId, long key, List<Expense> expenses) {
        List<Expense> expensesForUser1L = groupExpensesByUserId.get(key);
        assertThat(expensesForUser1L)
                .isEqualTo(
                        expenses.stream()
                                .filter(expense -> expense.getUserId() == key)
                                .collect(Collectors.toList()));
    }

    @Test
    public void shouldReturnExpensesGroupForUser() {
        // given
        StreamsTask streamsTask = new StreamsTask();
        List<User> users = takeUserList();
        List<Expense> expenses = takeExpenseList();

        // when
        Map<Long, List<Expense>> groupExpensesByUser = streamsTask.groupExpensesByUserId(users, expenses);

        // then
        List<Expense> expensesForUser1 = groupExpensesByUser.get(users.get(0));

        assertThat(expensesForUser1)
                .isEqualTo(
                        expenses.stream()
                                .filter(expense -> Objects.equals(expense.getUserId(), users.get(0).getId()))
                                .collect(Collectors.toList()));
    }

    @Test
    public void shouldReturnAverage22_25() {
        StreamsTask streamsTask = new StreamsTask();
        List<User> users = takeUserList();

        Double averageMenAge = streamsTask.averageMenAge(users);

        assertThat(averageMenAge).isEqualTo(22.25);
    }

    @Test
    public void shouldReturnAverage32_5() {
        StreamsTask streamsTask = new StreamsTask();
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "Alicja", 20));
        users.add(new User(2L, "Dominik", 15));
        users.add(new User(3L, "Patrycja", 25));
        users.add(new User(4L, "Marcin", 50));

        Double averageMenAge = streamsTask.averageMenAge(users);

        assertThat(averageMenAge).isEqualTo(32.5);
    }
}
