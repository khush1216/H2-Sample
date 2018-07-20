package com.javaProject.internalDB.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {
	
	@Autowired
    JobLauncher launcher;
      
    @Autowired
    Job job;
      
    
    @RequestMapping("/insertCSV")
    public String startJob() throws Exception {
    	
    	JobParameters parameters = new JobParametersBuilder()
              .addString("JobID", String.valueOf(System.currentTimeMillis()))
              .toJobParameters();
    	launcher.run(job, parameters);
      
      return "Job Launched!";
    	
    	
    }

}
