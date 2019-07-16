package com.cgi_ts.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="TIMESHEET")
public class TimeSheet {
	
	@Id
	@Column(name="SHEET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sheetId;

	@Column(name="START_DATE", nullable = false)
	private Timestamp startDate;
	
	@Column(name="SUBMITTED", nullable = false)
	private boolean submitted;
	
	@Column(name="EMPLOYEE_NAME", nullable = true)
	private String employeeName;
	
	@OneToMany(mappedBy = "timeSheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TimeSheetDay.class)
	private List<TimeSheetDay> timeSheetDays;
	
	public TimeSheet() {
		super();
	}
	
	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public List<TimeSheetDay> getTimeSheetDays() {
		return timeSheetDays;
	}
	
	public void addTimeSheetDay(TimeSheetDay timeSheetDay) {
		if (timeSheetDays == null) {
			timeSheetDays = new ArrayList();
		}
		timeSheetDay.setTimeSheet(this);
		timeSheetDays.add(timeSheetDay);
	}

	public void setTimeSheetDays(List<TimeSheetDay> timeSheetDays) {
		this.timeSheetDays = timeSheetDays;
	}

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(long sheetId) {
		this.sheetId = sheetId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String toString() {
		return "sheetId:["+sheetId+"] employeeName:["+employeeName+"] startDate["+startDate+"] timeSheetDays:["+timeSheetDays+"]";
	}
}
