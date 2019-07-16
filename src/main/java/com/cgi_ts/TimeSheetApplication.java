package com.cgi_ts;

import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeSheetApplication {
	private static final Logger logger = Logger.getLogger(TimeSheetApplication.class);

	public static void main(String[] args) {
		logger.debug("Starting TimeSheet Application");
		SpringApplication.run(TimeSheetApplication.class, args);
		logger.debug("after run");
	}

}
