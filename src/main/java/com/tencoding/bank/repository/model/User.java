package com.tencoding.bank.repository.model;

import java.security.Timestamp;

import lombok.Data;

//@Setter @Getter 동시에 사용
@Data
public class User {
	
	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private Timestamp createdAt;
	
}
