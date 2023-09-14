package com.tencoding.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	// 주소 설계
	// GET localhost:80/main-page
	@GetMapping("main-page")
	public String mainPage() {
		// 뷰 리졸버 실행
		// yml 파일 
		// prefix: /WEB-INF/view
		// subfix: .jsp 			
		// = /WEB-INF/view/layout/main.jsp
		return "layout/main";
	}
}
