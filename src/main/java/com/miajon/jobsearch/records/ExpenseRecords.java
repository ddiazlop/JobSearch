package com.miajon.jobsearch.records;

public class ExpenseRecords {
    public record ExpenseOverview(Double total, Double monthly, Double prediction) {
    }

    public record ExpensesByMonth(String month, Double amount) {
    }
}
