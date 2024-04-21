package com.miajon.jobsearch.records;

import com.miajon.jobsearch.model.enums.ExpenseType;

public class AmountRecords {
    /**
     * ExpensesByType
     */
    public record ExpensesByType(Double amount, ExpenseType type) {
        public ExpensesByType parseToAbs() {
            return new ExpensesByType(Math.abs(amount), type);
        }

        public String toJson() {
            return "{\"type\": \"" + type + "\", \"amount\": " + amount + "}";
        }
    }
}
