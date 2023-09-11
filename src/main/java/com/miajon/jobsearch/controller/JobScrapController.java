package com.miajon.jobsearch.controller;

import com.miajon.jobsearch.model.Job;
import com.miajon.jobsearch.service.JobService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JobScrapController {

    @Autowired
    private JobService jobService;

    private static final String URL1 = "https://www.linkedin.com/jobs/search/?currentJobId=3711529632&distance=25&f_TPR=r86400&geoId=105646813&keywords=software%20engineer";

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(JobScrapController.class);

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("message", "Hello World 5");
        return "home";
    }

    @GetMapping("/searchJobs")
    public String searchJobs(Model model) {
        Document doc = null;
        List<Job> jobs = new ArrayList<>();

        try {
            doc = Jsoup.connect(URL1)
                    .userAgent("Mozilla/5.0 (Windows NT 11.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9,es;q=0.8")
                    .get();
            Elements htmlJobs = doc.select("div.base-search-card__info");

            for (Element job : htmlJobs) {
                Job j = new Job(
                        job.select("h3.base-search-card__title").text(),
                        job.select("h4.base-search-card__subtitle").text(),
                        job.select("span.job-search-card__location").text()
                );
                jobs.add(j);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Saving jobs to MongoDB with filtering & add to the page itself.
        jobs.forEach(job -> jobService.saveJob(job));

        // JUst checking if the jobs are saved in the DB.
        logger.info("Jobs found: {}", jobService.findAllJobs().size());


        model.addAttribute("message", "Hello World 5");
        return "home";
    }

}
