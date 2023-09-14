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
    private Integer likelihood;

    public Job(String title, String company, String location){
        this.title = title;
        this.company = company;
        this.location = location;
        this.likelihood = 0;
    }


    public String toString() {
        return "Job: " + title + " at " + company + " in " + location + " [" + likelihood + "]";
    }

}
