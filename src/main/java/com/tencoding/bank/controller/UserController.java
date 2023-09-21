package com.tencoding.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.dto.SignInFormDto;
import com.tencoding.bank.dto.SignUpFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.repository.model.User;
import com.tencoding.bank.service.UserService;
import com.tencoding.bank.utills.Define;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired // DI 처리
	private UserService userService;
	
	@Autowired // DI 처리
	private HttpSession session;
	
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
	// 회원가입 처리
	// @param signUpFormDto
	// @return 리다이렉트 처리 - 로그인 페이지
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {
		// 1. 유효성 검사(사용자 입력이 다 되었는지)
		if (signUpFormDto.getUsername() == null 
				|| signUpFormDto.getUsername().isEmpty()) {
			// @Controller 라서 원래는 @ControllerAdvice 를 타야하는데
			// @Order 이라는 어노테이션을 이용하여 우선순위를 정해주어서
			// CustomRestfulException 으로 들어가게 됨.
			throw new CustomRestfulException("username을 입력해주세요", HttpStatus.BAD_REQUEST);
		}

		if (signUpFormDto.getPassword() == null 
				|| signUpFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력해주세요", HttpStatus.BAD_REQUEST);
		}

		if (signUpFormDto.getFullname() == null 
				|| signUpFormDto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		// 로직 추가 -- 서비스 호출
		userService.signUp(signUpFormDto);
		
		// 정상 처리가 되었다면 웹브라우저에게 return 을 시킴
		return "redirect:/user/sign-in";
	}
	
	/**
	 * 로그인 로직 처리
	 * @param signInFromDto
	 * @return 계좌 리스트 페이지로 return;
	 */
	
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto signInFormDto) {
		
		// 1. 유효성 검사
		if(signInFormDto.getUsername() == null || signInFormDto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력해주세요!", HttpStatus.BAD_REQUEST);
		}
		if(signInFormDto.getPassword() == null || signInFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력해주세요!", HttpStatus.BAD_REQUEST);
		}
		
		// 2. 서비스 -> 인증된 사용자 여부 확인
		User principal = userService.signIn(signInFormDto);
		principal.setPassword(null);
		
		// 3. 쿠키 + 세션
		session.setAttribute(Define.PRINCIPAL, principal);
		
		return "redirect:/account/list";

	}
	
	/**
	 * 로그아웃 처리
	 * @return 리다이렉트 - 로그인 페이지 이동
	 */
	@GetMapping("/logout")
	public String logout() {
		// 세션 만료 시키는 메서드
		session.invalidate();
		return "redirect:/user/sign-in";
	}
	
}
