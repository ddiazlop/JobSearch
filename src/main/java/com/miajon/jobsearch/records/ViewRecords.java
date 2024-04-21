package com.miajon.jobsearch.records;

import com.miajon.jobsearch.records.ExpenseRecords.ExpensesAndIncomeByMonth;
import com.miajon.jobsearch.records.ExpenseRecords.ExpensesByType;

import java.util.List;

public class ViewRecords {
    /**
     * DashboardView
     */
    public record DashboardViewData(ExpensesAndIncomeByMonth expensesAndIncomeByMonth,
            List<ExpensesByType> expensesByType) {

        public static DashboardViewData of(ExpensesAndIncomeByMonth expensesAndIncomeByMonth,
                List<ExpensesByType> expensesByType) {
            return new DashboardViewData(expensesAndIncomeByMonth, expensesByType);
        }

        public String toJson() {
            return expensesAndIncomeByMonth.toJson().replaceAll("}$",
                    ",\"expensesByType\": " + expensesByType.stream().map(ExpensesByType::toJson).toList() + "}");
        }
    }
}
