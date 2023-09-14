package com.tencoding.bank.repository.model;

import java.security.Timestamp;

import lombok.Data;

@Data
public class Account {
	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createAt;
	
	// 출금기능
	public void withdraw(Long amount) {
		this.balance -= amount;
	}
	
	// 입금기능
	public void deposit(Long amount) {
		this.balance += amount;
	}
	
	// TODO: 개발 예정
	// 패스워드 체크
	// 잔액 조회
	// 계좌 정보 확인
}
