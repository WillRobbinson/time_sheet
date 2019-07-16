package com.cgi_ts.ws;

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
