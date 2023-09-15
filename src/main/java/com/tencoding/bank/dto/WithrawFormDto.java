package com.tencoding.bank.dto;

import lombok.Data;

@Data
public class WithrawFormDto {
	private Long amount;
	private String wAccountNumber;
	private String wAccountPassword;
}
