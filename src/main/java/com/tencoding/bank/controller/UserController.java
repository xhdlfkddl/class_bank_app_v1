package com.tencoding.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.dto.SignUpFormDto;

@Controller
@RequestMapping("/user")
public class UserController {
	
	// 회원 가입
	// http://localhost:80/user/sign-up
	@GetMapping("/sign-up")
	public String signUp() {
		// /WEB-INF/view/user/signUp.jsp
		return "user/signUp";
	}
	
	// 로그인 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}
	
	// 회원가입 처리
	// http://localhost:80/user/sign-up
	// POST 로 처리 -> HTTP body에 데이터가 넘어올 수 있도록
	// name 속성을 이용하여 key=value 형태로 값이 넘어옴
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {
		System.out.println(signUpFormDto.toString());
		// 1. 유효성 검사
		// 사용자가 회원가입 버튼을 눌렀을 때 제대로 입력했는지 등을 controll 에서 검사함
		// 2. 사용자 이미지
		// 3. service 호출
		// 4. 정상 처리가 되었다면 웹브라우저에게 return 을 시킴
		return "redirect:/user/sign-up";
	}
}
