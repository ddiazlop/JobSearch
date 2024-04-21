package com.miajon.jobsearch.records;

import java.util.List;

import com.miajon.jobsearch.model.enums.ExpenseType;

public class ExpenseRecords {
    public record ExpenseOverview(Double total, Double monthly, Double prediction) {
    }

    public record ExpenseByMonth(String month, Double amount) implements Comparable<ExpenseByMonth> {
        private static final String[] MONTHS = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec" };

        public ExpenseByMonth mapAmountToAbs() {
            return new ExpenseByMonth(month, Math.abs(amount));
        }

        public ExpenseByMonth mapToMonthName() {
            String[] yearMonth = month.split("-");
            int monthIndex = Integer.parseInt(yearMonth[1]) - 1;
            return new ExpenseByMonth(yearMonth[0] + '-' + MONTHS[monthIndex], amount);
        }

        @Override
        public int compareTo(ExpenseByMonth other) {
            String[] yearMonth = month.split("-");
            String[] otherYearMonth = other.month.split("-");

            if (yearMonth[0].equals(otherYearMonth[0])) {
                return Integer.compare(Integer.parseInt(yearMonth[1]), Integer.parseInt(otherYearMonth[1]));
            }

            return Integer.compare(Integer.parseInt(yearMonth[0]), Integer.parseInt(otherYearMonth[0]));
        }

        public String toJson() {
            return "{\"month\": \"" + month + "\", \"amount\": " + amount + "}";
        }
    }

    public record ExpensesByType(Double amount, ExpenseType type) {
        public ExpensesByType(Double amount, ExpenseType type) {
            this.amount = Math.abs(amount);
            this.type = type;
        }

        public String toJson() {
            return "{\"type\": \"" + type + "\", \"amount\": " + amount + "}";
        }
    }

    public record ExpensesAndIncomeByMonth(List<ExpenseByMonth> expenses, List<ExpenseByMonth> income) {

        public ExpensesAndIncomeByMonth(List<ExpenseByMonth> expenses, List<ExpenseByMonth> income) {
            this.expenses = expenses;
            this.income = income;
            compensateLists();
        }

        public static ExpensesAndIncomeByMonth ofOrdered(List<ExpenseByMonth> expenses, List<ExpenseByMonth> income) {
            ExpensesAndIncomeByMonth expensesAndIncomeByMonth = new ExpensesAndIncomeByMonth(expenses, income);
            return expensesAndIncomeByMonth.getOrderedAmountsByMonthName();
        }

        private void compensateLists() {
            if (expenses.size() > income.size()) {
                for (ExpenseByMonth ExpenseByMonth : expenses) {
                    if (income.stream().noneMatch(income -> income.month().equals(ExpenseByMonth.month()))) {
                        income.add(new ExpenseByMonth(ExpenseByMonth.month(), 0.0));
                    }
                }
            } else if (income.size() > expenses.size()) {
                for (ExpenseByMonth ExpenseByMonth : income) {
                    if (expenses.stream().noneMatch(income -> income.month().equals(ExpenseByMonth.month()))) {
                        expenses.add(new ExpenseByMonth(ExpenseByMonth.month(), 0.0));
                    }
                }
            }
        }

        public ExpensesAndIncomeByMonth getOrderedAmountsByMonthName() {
            List<ExpenseByMonth> expensesPerMonthName = expenses.stream().sorted()
                    .map(expense -> expense.mapAmountToAbs().mapToMonthName()).toList();
            List<ExpenseByMonth> incomePerMonthName = income.stream().sorted()
                    .map(expense -> expense.mapAmountToAbs().mapToMonthName()).toList();

            return new ExpensesAndIncomeByMonth(expensesPerMonthName, incomePerMonthName);
        }

        public String toJson() {
            return "{\"expenses\": " + expenses.stream().map(ExpenseByMonth::toJson).toList() + ", \"income\": "
                    + income.stream().map(ExpenseByMonth::toJson).toList() + "}";
        }
    }
}
