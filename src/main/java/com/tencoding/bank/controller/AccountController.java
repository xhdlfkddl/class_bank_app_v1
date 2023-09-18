package com.tencoding.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.bank.handler.exception.CustomPageException;

@Controller
@RequestMapping("/account")
public class AccountController {
	// 계좌목록 페이지
	// http://localhost:80/account/
	// http://localhost:80/account/list
	// 둘다 가능하게 설정
	@GetMapping({"/list", ""})
	public String list() {
		return "account/list";
//		throw new CustomPageException("페이지가 존재하지않습니다.", HttpStatus.NOT_FOUND);
	}
	
	// 계좌 생성 페이지
	// http://localhost:80/account/save
	@GetMapping("/save")
	public String save() {
		return "account/save";
	}
	
	// 출금 페이지
	// http://localhost:80/account/withdraw
	@GetMapping("/withdraw")
	public String withdraw() {
		return "account/withdraw";
	}

	// 입금 페이지
	// http://localhost:80/account/deposit
	@GetMapping("/deposit")
	public String deposit() {
		return "account/deposit";
	}
	
	// 이체 페이지
	// http://localhost:80/account/transfer
	@GetMapping("/transfer")
	public String transfer() {			
		return "account/transfer";
	}
	
	// TODO: 수정필요!
	// 상세 페이지
	// http://localhost:80/account/detail/계좌고유번호
	@GetMapping("/detail")
	public String detail() {			
		return "account/detail";
	}
}
