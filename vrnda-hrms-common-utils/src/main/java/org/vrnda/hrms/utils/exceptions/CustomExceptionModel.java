package org.vrnda.hrms.utils.exceptions;


import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class CustomExceptionModel {

	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timeStamp;
	private final int status;
	public CustomExceptionModel(String message, 
								HttpStatus httpStatus, 
								ZonedDateTime timeStamp,
								int status) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.timeStamp = timeStamp;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public ZonedDateTime getTimeStamp() {
		return timeStamp;
	}
	public int getStatus() {
		return status;
	}
	
	
}
