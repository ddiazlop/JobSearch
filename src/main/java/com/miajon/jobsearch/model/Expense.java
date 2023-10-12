package com.miajon.jobsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Data
@Document(collection = "expenses")
public class Expense {

    @Id
    private String id;

    private final String concept;

    private final Integer amount;

    private final Date date;


    public Expense(String concept, Integer amount){
        this.concept = concept;
        this.amount = amount;
        this.date = Calendar.getInstance().getTime();

    }

}

