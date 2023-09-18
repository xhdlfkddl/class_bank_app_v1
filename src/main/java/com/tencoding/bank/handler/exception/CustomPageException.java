package com.tencoding.bank.handler.exception;

import org.springframework.http.HttpStatus;

public class CustomPageException extends RuntimeException {
	
	private HttpStatus status;
	
	public CustomPageException(String message, HttpStatus httpStatus) {
		super(message);
		this.status = httpStatus;
	}
	
}
