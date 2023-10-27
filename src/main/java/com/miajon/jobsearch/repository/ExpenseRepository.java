package com.miajon.jobsearch.repository;

import com.miajon.jobsearch.model.Expense;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    @Aggregation(pipeline = {
            "{$group: {_id: null, totalAmount: {$sum: '$amount'}}}",
            "{$project: { _id: 0, totalAmount: 1 }}"
    })
    public Double getTotalAmount();

    @Query("{monthly: true}")
    public Iterable<Expense> findMonthlyExpenses(Boolean monthly);

    @Aggregation(pipeline = {
            "{$match: {monthly: true}}",
            "{$group: {_id: null, totalAmount: {$sum: '$amount'}}}",
            "{$project: { _id: 0, totalAmount: 1 }}"
    })
    public Double getTotalMonthlyAmount();
}
