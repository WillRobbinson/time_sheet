package com.cgi_ts.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TimesheetNotFoundException extends RuntimeException {

	public TimesheetNotFoundException() {
		super();
	}

	public TimesheetNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TimesheetNotFoundException(final String message) {
		super(message);
	}

	public TimesheetNotFoundException(final Throwable cause) {
        super(cause);
    }
}
