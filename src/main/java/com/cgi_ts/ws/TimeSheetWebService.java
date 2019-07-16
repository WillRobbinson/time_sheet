package com.cgi_ts.ws;

import java.sql.Timestamp;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cgi_ts.TimeSheet;
import com.cgi_ts.TimeSheetDay;
import com.cgi_ts.store.TimeSheetRepository;

/**
 * This REST web service provide limited and specific access to a TimeSheet repository.
 */
@RestController
@RequestMapping("/api/timesheet")
public class TimeSheetWebService {

	private static final Logger logger = Logger.getLogger(TimeSheetWebService.class);
	
	@Autowired
	private TimeSheetRepository repo;
	
	/**
	 * Requirement: Given an OPTIONAL timesheet number, return all the data about a given timesheet, or all timesheets if none is specified.
	 * - This method represents the "all timesheets if none is specified" part.
	 * @return all stored timesheets.
	 */
	@RequestMapping(method= RequestMethod.GET)
    public Iterable<TimeSheet> findAll() {
		logger.info("findAll:");
        return repo.findAll();
    }

	/**
	 * Requirement: Given an OPTIONAL timesheet number, return all the data about a given timesheet, or all timesheets if none is specified.
	 * - This method represents the "Given an OPTIONAL timesheet number, return all the data about a given timesheet" part.
	 * @param id long the timesheet identifier.
	 * @return If a timesheet has been stored for this id, then that timesheet is returned, otherwise throw TimesheetNotFoundException.
	 */
	@GetMapping("/{id}")
	public TimeSheet findOne(@PathVariable long id) {
		logger.info("2.5");
		Optional<TimeSheet> optionalTimeSheet = repo.findById(id);
		if (optionalTimeSheet.isPresent()) {
			TimeSheet ts = optionalTimeSheet.get();
			for (TimeSheetDay tsd:ts.getTimeSheetDays()) {
				logger.info("2.6: tsd["+tsd+"]");				
			}
			logger.info("2.6.5");				
			return optionalTimeSheet.get();
		}
		logger.info("2.7");
		throw new TimesheetNotFoundException("id["+id+"] not found");
	}

	/**
	 * Requirement: This endpoint will take a start date as a parameter and create a new timesheet. It should return the ID of the new timesheet.
	 * @param date in Timestamp format.
	 * @return long the timesheet identifier for the created timesheet.
	 */
	@PostMapping("/{date}")
	@ResponseStatus(HttpStatus.CREATED)
	public long create(@PathVariable Timestamp date) {
		logger.info("create called.");
		TimeSheet timeSheet = new TimeSheet();
		timeSheet.setStartDate(date);
		TimeSheet timeSheet2 = repo.save(timeSheet);
		return timeSheet2.getSheetId();
	}

	/**
	 * Requirement: Given a timesheet number, mark the timesheet as submitted.
	 * @param sheetId the timesheet identifier.
	 * If a timeSheet does not exist for the given identifier throw a TimesheetNotFoundException.
	 */
	@PutMapping("/submit/{sheetId}")
	public void submit(@PathVariable Long sheetId) {
		TimeSheet timeSheet = repo.findById(sheetId).orElseThrow(TimesheetNotFoundException::new);
		timeSheet.setSubmitted(true);
		repo.save(timeSheet);
	}

	/**
	 * Requirement: Given a timesheet number, amount of time, and day index, update the timesheet with the given amount for the given day index.
	 * @param sheetId long timesheet identifier.
	 * @param amountOfTime in minutes.
	 * @param dayIndex 1...7 corresponding to Monday...Sunday.
	 */
	@PutMapping("/{sheetId}/{amountOfTime}/{dayIndex}")
	public void updateTimeSheet(@PathVariable long sheetId,@PathVariable int amountOfTime, @PathVariable int dayIndex) {
		logger.info("1.5");
    	TimeSheet timeSheet = repo.findById(sheetId).orElseThrow(TimesheetNotFoundException::new);
		if (dayIndex < 0 || dayIndex > 7) {
			throw new TimeSheetIndexOutOfRangeException("dayIndex: ["+dayIndex+"] must be 1-7:Monday...Sunday");
		}
		
		boolean dayIndexFound = false;
		for (TimeSheetDay timeSheetDay:timeSheet.getTimeSheetDays()) {
			if (timeSheetDay.getDayOfWeek() == dayIndex) {
				timeSheetDay.setMinutes(amountOfTime);
				dayIndexFound = true;
			}
		}
		if (!dayIndexFound) {
			TimeSheetDay timeSheetDay = new TimeSheetDay();
			timeSheetDay.setDayOfWeek(dayIndex);
			timeSheetDay.setMinutes(amountOfTime);
//			timeSheetDay.setSheetId(sheetId);
			timeSheet.addTimeSheetDay(timeSheetDay);
		}
		
		repo.save(timeSheet);
	}
}