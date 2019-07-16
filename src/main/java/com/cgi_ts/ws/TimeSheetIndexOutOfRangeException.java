package com.cgi_ts.ws;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimeSheetIndexOutOfRangeException  extends RuntimeException {

	public TimeSheetIndexOutOfRangeException() {
		super();
	}

	public TimeSheetIndexOutOfRangeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TimeSheetIndexOutOfRangeException(final String message) {
		super(message);
	}

	public TimeSheetIndexOutOfRangeException(final Throwable cause) {
        super(cause);
    }
}