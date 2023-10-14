package com.miajon.jobsearch.model;

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



    public Expense(String concept, Double amount, Boolean monthly) {
        this.concept = concept;
        this.amount = amount;
        this.monthly = monthly;
        this.date = LocalDate.now();
    }

}

