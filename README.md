1. This application is a Spring Boot application and uses H2 database for storing the records from the csv file.
2. The application was built in Eclipse using Maven; please import using pom.xml if running in Intellij.
3. To run the application you must have:
	a. Eclipse/Intellij Ultimate with Maven installed
	b. Java 8.
	
4. No pre-requisite steps required. Simply run the application. In order to start the data transfer, migrate to localhost:8080/insertCSV on your browser. 
5. The logs will be printed on the console - stating which data is good and which is bad.

5. At the end of the process - migrate to localhost:8080/console to view the database and successful records. Type the query 
- "select * from person"

6. The bad-data file and the logfile.txt is stored in the same project folder.

Please contact me if you face any issues while running the application.
	
References to build the application:
Java Documentation, H2 documentation, Spring Batch documentation.
