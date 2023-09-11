package com.miajon.jobsearch.repository;

import com.miajon.jobsearch.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {

    @Query("{}")
    List<Job> findAll();
}
