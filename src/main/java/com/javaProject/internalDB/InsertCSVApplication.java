package com.javaProject.internalDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InsertCSVApplication
{
	
	public static Long badDataTimestamp, receivedRecords =0L, successRecords = 0L, badRecords = 0L;
	
    public static void main(String[] args)
    {
    	badDataTimestamp = System.currentTimeMillis();
        SpringApplication.run(InsertCSVApplication.class, args);
        
        System.out.println("After application has printed the records!");
    }
    
      
    
}