package com.miajon.jobsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "jobs")
public class Job {

    @Id
    private String id;

    private final String title;
    private final String company;
    private final String location;

    public String toString() {
        return "Job: " + title + " at " + company + " in " + location;
    }



}
