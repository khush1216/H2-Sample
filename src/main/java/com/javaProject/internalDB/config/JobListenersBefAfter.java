package com.javaProject.internalDB.config;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import com.javaProject.internalDB.InsertCSVApplication;

@Component
public class JobListenersBefAfter implements JobExecutionListener{
	
	 public void beforeJob(JobExecution jobExecution) {
	        System.out.println("STARTING INSERTION OF RECORDS!");
	    }
	 
	 public void afterJob(JobExecution jobExecution) {
	        System.out.println("Insertion Completed!");
	        
	        try {
	        	BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(".\\logFile.txt"), "UTF-8"));
	    		bufferedWriter.write("Total Received Records : " + InsertCSVApplication.receivedRecords.toString());
	    		bufferedWriter.newLine();
	    		bufferedWriter.write("Total Successful Insertions : " + InsertCSVApplication.successRecords.toString());
	    		bufferedWriter.newLine();
	    		bufferedWriter.write("Total Failed Insertions : " + InsertCSVApplication.badRecords.toString());
	    		bufferedWriter.newLine();
	    	
	    		bufferedWriter.flush();
	    		bufferedWriter.close();
	        	
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	    }

}
