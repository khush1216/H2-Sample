package com.javaProject.internalDB.config;
 
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.springframework.batch.item.ItemProcessor;

import com.javaProject.internalDB.InsertCSVApplication;
import com.javaProject.internalDB.model.Person;
 
public class ItemProcessorModelClass implements ItemProcessor<Person, Person>
{
	String fileName = ".\\bad-data" + Long.toString(InsertCSVApplication.badDataTimestamp) + ".csv";
	
    public Person process(Person employee) throws Exception
    {
    	InsertCSVApplication.receivedRecords ++;
    	if(employee.isAnyFieldNull()) {
    		
    		InsertCSVApplication.badRecords ++;
    		System.out.println("Not inserting employee : " + employee);
    		writeToBadDataFile(employee);
    		return null;
    	}
    	else {
    		
    		InsertCSVApplication.successRecords ++;
    		System.out.println("Inserting employee : " + employee);
    		return employee;
    	}
    }
        
    public void writeToBadDataFile(Person emp) {
    	
    	try {
    		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"));
    		bufferedWriter.write(emp.toString());
    		bufferedWriter.newLine();
    		
    		bufferedWriter.flush();
    		bufferedWriter.close();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}