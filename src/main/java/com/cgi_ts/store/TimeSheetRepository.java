package com.cgi_ts.store;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgi_ts.data.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {}
