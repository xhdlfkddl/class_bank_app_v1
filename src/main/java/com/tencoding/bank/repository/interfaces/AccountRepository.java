package com.tencoding.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tencoding.bank.repository.model.Account;

@Mapper
public interface AccountRepository {
	public int insert(Account account);
	public int updateById(Integer id);
	public int deleteById(Integer id);
	
	public List<Account> findAll();
	public Account findById(Integer id);
	
	public List<Account> findByUserId(Integer principalId);
	// 계좌번호로 계좌 조회
	public Account findByNumber(Integer number);
}
