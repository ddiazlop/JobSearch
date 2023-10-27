package com.miajon.jobsearch.model;

import com.miajon.jobsearch.model.enums.ExpenseType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;

    private String concept;

    private Double amount;

    private LocalDate date;

    private Boolean monthly;

    private ExpenseType type;



    public Expense(String concept, Double amount, Boolean monthly) {
        this.concept = concept;
        this.amount = amount;
        this.monthly = monthly;
        this.date = LocalDate.now();

        for (ExpenseType expenseType : ExpenseType.values()) {
            if (concept.toUpperCase().contains(expenseType.name())) {
                this.type = expenseType;
                break;
            }
        }
    }

}

