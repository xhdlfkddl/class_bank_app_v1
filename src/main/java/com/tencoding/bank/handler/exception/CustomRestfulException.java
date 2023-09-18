package com.tencoding.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

// class 정의
@Getter // IoC 대상이 아님 (= 필요할 때 직접 new 하여 사용할 예정)
public class CustomRestfulException extends RuntimeException {

	private HttpStatus status;
	
	public CustomRestfulException(String message, HttpStatus httpStatus) {
		super(message);
		this.status = status;
	}
	
}
