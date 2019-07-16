package com.cgi_ts;

import static org.junit.Assert.assertTrue;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSheetApplicationTests {
	private static final Logger logger = Logger.getLogger(TimeSheetApplicationTests.class);

	@Test
	public void contextLoads() {
		logger.info("I am logging");
		assertTrue(true);
		logger.info("I am DONE logging");
	}

}
