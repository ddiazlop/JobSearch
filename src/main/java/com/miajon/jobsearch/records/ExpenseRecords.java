package com.miajon.jobsearch.records;

public class ExpenseRecords {
    public record ExpenseOverview(Double total, Double monthly, Double prediction) {
    }

    public record ExpensesByMonth(String month, Double amount) implements Comparable<ExpensesByMonth> {
        private static final String[] MONTHS = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec" };

        public ExpensesByMonth mapAmountToAbs() {
            return new ExpensesByMonth(month, Math.abs(amount));
        }

        public ExpensesByMonth mapToMonthName() {
            String[] yearMonth = month.split("-");
            int monthIndex = Integer.parseInt(yearMonth[1]) - 1;
            return new ExpensesByMonth(yearMonth[0] + '-' + MONTHS[monthIndex], amount);
        }

        @Override
        public int compareTo(ExpensesByMonth other) {
            String[] yearMonth = month.split("-");
            String[] otherYearMonth = other.month.split("-");

            if (yearMonth[0].equals(otherYearMonth[0])) {
                return Integer.compare(Integer.parseInt(yearMonth[1]), Integer.parseInt(otherYearMonth[1]));
            }

            return Integer.compare(Integer.parseInt(yearMonth[0]), Integer.parseInt(otherYearMonth[0]));
        }
    }
}
