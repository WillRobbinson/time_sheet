package com.cgi_ts.ws;

public class TimeSheetIdMismatchException  extends RuntimeException {

	public TimeSheetIdMismatchException() {
		super();
	}

	public TimeSheetIdMismatchException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TimeSheetIdMismatchException(final String message) {
		super(message);
	}

	public TimeSheetIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
