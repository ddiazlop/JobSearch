package com.miajon.jobsearch.repository;

import com.miajon.jobsearch.model.Expense;
import com.miajon.jobsearch.records.AmountRecords;
import com.miajon.jobsearch.records.ExpenseRecords;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

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
                        "{$sort: {date: -1, _id: -1}}",
                        "{$limit: 10}",
        })
        List<Expense> findLastTenExpenses();

        @Aggregation(pipeline = {
                        "{$match: {monthly: true}}",
                        "{$group: {_id: null, totalAmount: {$sum: '$amount'}}}",
                        "{$project: { _id: 0, totalAmount: 1 }}"
        })
        Double getTotalMonthlyAmount();

        @Aggregation(pipeline = {
                        "{$match: {amount: {$gt: 0}}}",
                        "{$group: {_id: {$dateToString: {format: '%Y-%m', date: '$date'}}, totalAmount: {$sum: '$amount'}}}",
                        "{$project: { _id: 0, month: '$_id', amount: '$totalAmount' }}"
        })
        List<ExpenseRecords.ExpenseByMonth> getIncomePerMonth();

        @Aggregation(pipeline = {
                        "{$match: {amount: {$lt: 0}}}",
                        "{$group: {_id: {$dateToString: {format: '%Y-%m', date: '$date'}}, totalAmount: {$sum: '$amount'}}}",
                        "{$project: { _id: 0, month: '$_id', amount: '$totalAmount' }}"
        })
        List<ExpenseRecords.ExpenseByMonth> getExpensesPerMonth();

        @Aggregation(pipeline = {
                        "{$match: {amount: {$lt: 0}}}",
                        "{$group: {_id: '$type', totalAmount: {$sum: '$amount'}}}",
                        "{$project: {_id: 0, type: '$_id', amount: '$totalAmount'}}"
        })
        List<AmountRecords.ExpensesByType> getAmountsByType();
}
