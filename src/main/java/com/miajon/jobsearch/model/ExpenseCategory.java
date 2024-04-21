package com.miajon.jobsearch.model;

import java.util.List;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "expenseCategories")
public class ExpenseCategory {
    @Id
    private String id;

    private String name;

    private List<String> keywords;

    public ExpenseCategory(String name, String keywords) {
        this.name = name;
        this.keywords = Arrays.stream(keywords.split(",")).map(keyword -> keyword.trim()).toList();
    }
}
