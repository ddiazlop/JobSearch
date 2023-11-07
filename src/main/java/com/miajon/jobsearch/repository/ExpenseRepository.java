package com.miajon.jobsearch.repository;

import com.miajon.jobsearch.model.Expense;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


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

    //TODO: MAKE THIS ALSO COUNT THE YEAR
    @Aggregation(pipeline = {
            "{$group: {_id: {$month: '$date'}, totalAmount: {$sum: '$amount'}}}",
            "{$project: { _id: 0, month: '$_id', totalAmount: 1 }}"
    })
    List<String> getIncomePerMonth();
}
