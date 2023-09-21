package com.tencoding.bank.handler;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.handler.exception.UnAuthorizedException;

/*
 	예외 발생 시 (Json, Xml)
 	데이터를 가공하여 내려줄 수 있음.
 */
// IoC의 대상: 제어의 역정의 대상 + AoP 대상
@RestControllerAdvice
@Order(1)
public class MyRestfulExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("==== 예외 발생!! =====");
		System.out.println(e.getMessage());
		System.out.println("====================");
	}
	
	// 사용자 정의 예외 클래스 활용
	// 괄호 안의 class가 실행되었을 때 해당 메서드가 실행
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		// String <-- 메모리에 계속 올라감
		// 하나의 참조 값에 계속 할당되기 때문에 메모리 관리에 효율적임
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert(' " + e.getMessage() + " ');"); // 문자열 안에 반드시 ; 붙일 것!!
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString();
	}
	
	// 로그인을 하지 않았을 경우 예외처리
	@ExceptionHandler(UnAuthorizedException.class)
	public String notLoginException(UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert(' " + e.getMessage() + " ');"); // 문자열 안에 반드시 ; 붙일 것!!
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString();
	}
}
