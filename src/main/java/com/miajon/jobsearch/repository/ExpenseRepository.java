package com.miajon.jobsearch.repository;

import com.miajon.jobsearch.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

}
