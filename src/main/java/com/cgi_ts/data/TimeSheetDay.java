package com.cgi_ts.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="TIMESHEET_DAY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSheetDay {
	
	@Id
	@Column(name="TIMESHEET_DAY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="SHEET_ID")
	private TimeSheet timeSheet;

	@Column(name="DAY_OF_WEEK", nullable = true)
	private int dayOfWeek;
	
	@Column(name="MINUTES", nullable = true)
	private int minutes;
	
	public TimeSheetDay() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getTimeSheetId() {
		return timeSheet.getSheetId();
	}

	public void setTimeSheet(TimeSheet timeSheet) {
		this.timeSheet = timeSheet;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public String toString() {
		return "id["+id+"] dayOfWeek["+dayOfWeek+"] minutes["+minutes+"] sheetId["+timeSheet.getSheetId()+"]";
	}

	public void setSheetId(long sheetId) {
		if(this.timeSheet == null) {
			this.timeSheet = new TimeSheet();
		}
		this.timeSheet.setSheetId(sheetId);
	}
}
