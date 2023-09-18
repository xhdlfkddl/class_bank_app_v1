package com.tencoding.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.tencoding.bank.handler.exception.CustomPageException;

/*
  	view 렌더링을 위해 ModelAndView
  	객체를 반환하도록 설계할 때 사용
  	예외 page를 return 하도록 설계
 */

@ControllerAdvice // IoC의 대상
public class MyPageExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("==== 예외 발생!! =====");
		System.out.println(e.getMessage());
		System.out.println("====================");
	}
	
	// 사용자 정의 클래스 활용
	@ExceptionHandler(CustomPageException.class)
	public ModelAndView handlRuntimePageException(CustomPageException e) {
		System.out.println("확인!");
		// ModelAndView 활용방법 - new 할 때 페이지 명시해주기!!
		ModelAndView modelAndView = new ModelAndView("errorPage");
		modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND.value());
		modelAndView.addObject("message", e.getMessage());
		
		return modelAndView;
	}
}
