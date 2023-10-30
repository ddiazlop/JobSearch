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
    Double getTotalAmount();

    @Query("{monthly: true}")
    Iterable<Expense> findMonthlyExpenses(Boolean monthly);

    @Query(sort = "{date: -1}")
    Iterable<Expense> findAllByOrderByDateDesc();

    @Aggregation(pipeline = {
            "{$match: {monthly: true}}",
            "{$group: {_id: null, totalAmount: {$sum: '$amount'}}}",
            "{$project: { _id: 0, totalAmount: 1 }}"
    })
    Double getTotalMonthlyAmount();
}
