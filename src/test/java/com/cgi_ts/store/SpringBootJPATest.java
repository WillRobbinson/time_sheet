package com.cgi_ts.store;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cgi_ts.TimeSheet;
import com.cgi_ts.TimeSheetDay;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootJPATest {
     
	private static final Logger logger = Logger.getLogger(SpringBootJPATest.class);
    @Autowired
    private TimeSheetRepository timeSheetRepository;
 
    @Test
    public void findAllTimeSheets() {
        List<TimeSheet> timeSheets = 
        		timeSheetRepository.findAll();
        for (TimeSheet ts:timeSheets) {
            logger.info("Id was["+ts.getSheetId()+"] employeeName["+ts.getEmployeeName()+"] timeSheetDays["+ts.getTimeSheetDays()+"]");
            if ("JOHN".equals(ts.getEmployeeName())) {
            	assertTrue(!ts.isSubmitted());
            } else if ("RINGO".equals(ts.getEmployeeName())) {
            	assertTrue(ts.isSubmitted());
            }
        }
    }
    
    @Test
    public void createUpdateEmployeeTimeSheet() {
    	TimeSheet newTimeSheet = new TimeSheet();
    	newTimeSheet.setEmployeeName("James Tiberius Kirk");
    	TimeSheet ts = null;
    	try {
    		ts = timeSheetRepository.save(newTimeSheet);
    		fail("Should have failed because a timesheet needs a date.");
    	} catch (DataIntegrityViolationException dive) {}
    	newTimeSheet.setStartDate(new Timestamp(System.currentTimeMillis()));
		ts = timeSheetRepository.save(newTimeSheet);
		logger.info("Created TimeSheet["+ts+"]");
    	Optional<TimeSheet> modifiedTS = timeSheetRepository.findById(ts.getSheetId());
    	logger.info("modifiedTS["+modifiedTS.get()+"]");
    	
    	TimeSheetDay timeSheetDay = new TimeSheetDay();
    	timeSheetDay.setDayOfWeek(1);
    	timeSheetDay.setMinutes(30);
    	modifiedTS.get().addTimeSheetDay(timeSheetDay);    	
    	timeSheetRepository.save(modifiedTS.get());
    	
    	modifiedTS = timeSheetRepository.findById(ts.getSheetId());
    	TimeSheetDay tsd = modifiedTS.get().getTimeSheetDays().get(0);
    	logger.info("modifiedTS dayOfWeek["+tsd.getDayOfWeek()+"] minutes["+tsd.getMinutes()+"]");
    	assertTrue(tsd.getDayOfWeek() == 1);
    	assertTrue(tsd.getMinutes() == 30);    	
    }
    
    @Test
    public void testSubmitTimeSheet() {
    	TimeSheet newTimeSheet = new TimeSheet();
    	newTimeSheet.setEmployeeName("Scotty");
    	TimeSheet ts = null;
    	newTimeSheet.setStartDate(new Timestamp(System.currentTimeMillis()));
		ts = timeSheetRepository.save(newTimeSheet);
		assertFalse(ts.isSubmitted());
		logger.info("Created TimeSheet["+ts+"]");
		ts.setSubmitted(true);
		ts = timeSheetRepository.save(newTimeSheet);
		assertTrue(ts.isSubmitted());
    }
    
    @Test
    public void testDeleteTimeSheet() {
    	Optional<TimeSheet> ts = timeSheetRepository.findById(1L);
    	assertTrue(ts.isPresent());
    	timeSheetRepository.delete(ts.get());
    	ts = timeSheetRepository.findById(1L);
    	assertTrue(!ts.isPresent());    	
    }
}
