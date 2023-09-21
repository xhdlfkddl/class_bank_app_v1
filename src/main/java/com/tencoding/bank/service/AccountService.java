package com.tencoding.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.bank.dto.DepositFormDto;
import com.tencoding.bank.dto.HistoryDto;
import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.dto.TransferFormDto;
import com.tencoding.bank.dto.WithrawFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.repository.interfaces.AccountRepository;
import com.tencoding.bank.repository.interfaces.HistoryRepository;
import com.tencoding.bank.repository.model.Account;
import com.tencoding.bank.repository.model.History;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Transactional
	public void createAccount(SaveFormDto saveFormDto, int principalId) {
		
		Account account = new Account();
		account.setNumber(saveFormDto.getNumber());
		account.setPassword(saveFormDto.getPassword());
		account.setBalance(saveFormDto.getBalance());
		account.setUserId(principalId);
		
		int resultRowCount = accountRepository.insert(account);
		if(resultRowCount != 1) {
			throw new CustomRestfulException("계좌 생성에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<Account> readAccountList(int userId) {
		List<Account> account = accountRepository.findByUserId(userId);
		
		return account;
	}
	
	// 출금 기능 로직을 고민보기 
	// 1. 계좌 존재 여부 확인 -- select query 
	// 2. 본인 계좌 여부 확인 
	// 3. 계좌 비번 확인 
	// 4. 잔액 여부 확인 
	// 5. 출금 처리 --> update query 
	// 6. 거래 내역 등록 --> insert query 
	// 7. 트랜잭션 처리 
	@Transactional
	public void updateAccountWithdarw(WithrawFormDto withdrawFormDto, Integer id) {
	  Account accountEnitity = accountRepository.findByNumber(withdrawFormDto.getWAccountNumber());
	  if(accountEnitity == null) { // 1 
		  throw new CustomRestfulException("해당 계좌가 없습니다", HttpStatus.BAD_REQUEST);
	  }
	  if(accountEnitity.getUserId() != id ) { // 2
		  throw new CustomRestfulException("본인 소유 계좌가 아닙니다", HttpStatus.BAD_REQUEST);
	  }
	  // 3
	  if(accountEnitity.getPassword().equals(withdrawFormDto.getWAccountPassword()) == false) {
		  throw new CustomRestfulException("출금 계좌 비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST); 
	  }
	  // 4 
	  if(accountEnitity.getBalance() < withdrawFormDto.getAmount()) {
		  throw new CustomRestfulException("계좌 잔액이 부족합니다", HttpStatus.BAD_REQUEST);
	  }
	  // 5 -> update 쿼리 (모델 객체 상태 변경 --> 객체를 다시 던지기) 
	  accountEnitity.withdraw(withdrawFormDto.getAmount());
	  accountRepository.updateById(accountEnitity);
	  // 6 - 거내내역 등록 History 객체 생성 
	  History history = new History();
	  history.setAmount(withdrawFormDto.getAmount());
	  // 출금 시점에 해당 계좌에 잔액을 입력 
	  history.setWBalance(accountEnitity.getBalance());
	  history.setDBalance(null);
	  history.setWAccountId(accountEnitity.getId());
	  history.setDAccountId(null);
	  
	  int resultRowCount = historyRepository.insert(history);
	  if(resultRowCount != 1) {
		  throw new CustomRestfulException("정상 처리 되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

	// 입금 기능 로직 생각 해보기
		// 1. 계좌 존재 여부 확인 -- select query
		// 2. 입금 처리 --> update query
		// 3. 거래 내역 등록 --> insert query
		public void updateAccountDeposit(DepositFormDto depositFormDto) {

			Account accountEntity = accountRepository.findByNumber(depositFormDto.getDAccountNumber());
			if (accountEntity == null) {
				throw new CustomRestfulException("해당 계좌가 존재하지 않습니다", HttpStatus.BAD_REQUEST);
			}

			// 객체 상태값 변경 처리
			accountEntity.deposit(depositFormDto.getAmount());
			accountRepository.updateById(accountEntity); // update 처리
			// 거래 내역 등록 <-- DB 분석
			History history = new History();
			history.setAmount(depositFormDto.getAmount());
			history.setWBalance(null);
			// 현재 입금 되었을 때 잔액을 기록 
			history.setDBalance(accountEntity.getBalance());
			history.setWAccountId(null);
			history.setDAccountId(accountEntity.getId());
			
			int resultRowCount = historyRepository.insert(history);
			if(resultRowCount != 1) {
				throw new CustomRestfulException("정상 처리가 되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			

		}
		
		// 이체 로직 고민해보기 
		// 1. 출금 계좌 존재 여부 확인 - select 
		// 2. 입금 계좌 존재 여부 확인 - select 
		// 3. 출금 계좌 본인 소유 확인 - 객체 상태값 확인 (id)  / 객체( 1 - select )
		// 4. 출금 계좌 비번 확인 - TransferFormDto(비번) / 모델 (비번) 
		// 5. 출금 계좌 잔액 여부 확인 - DTO / 모델 객체 
		// 6. 출금 계좌 잔액 - update 
		// 7. 입금 계좌 잔액 - update 
		// 8. 거래 내역 등록
		// 9. 트랜잭션 처리 
		@Transactional
		public void updateAccountTransfer(TransferFormDto transferFormDto, Integer id) {
			// 1
			Account withdrawAccountEntity = accountRepository.findByNumber(transferFormDto.getWAccountNumber());
			if(withdrawAccountEntity == null) {
				throw new CustomRestfulException("출금 계좌가 존재하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 2 
			Account depositAccountEntity = accountRepository.findByNumber(transferFormDto.getDAccountNumber());
			if(depositAccountEntity == null) {
				throw new CustomRestfulException("입금 계좌가 존재하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 3. 출금 계좌 본인 소유 확인
			withdrawAccountEntity.checkOwner(id);
			// 4. 출금 계좌 비번 확인
			withdrawAccountEntity.checkPassword(transferFormDto.getWAccountPassword());
			// 5. 출금 계좌 잔액 여부 확인
			withdrawAccountEntity.checkBalance(transferFormDto.getAmount());
			// 6. 출금 계좌 잔액 상태 값 변경 처리 
			withdrawAccountEntity.withdraw(transferFormDto.getAmount());
			// update 처리 
			accountRepository.updateById(withdrawAccountEntity);
			// 7. 입금 계좌 잔액 상태 값 변경 처리 
			depositAccountEntity.deposit(transferFormDto.getAmount());
			// update 처리 
			accountRepository.updateById(depositAccountEntity);
			// 8 
			History history = new History();
			history.setAmount(transferFormDto.getAmount());
			history.setWAccountId(withdrawAccountEntity.getId());
			history.setDAccountId(depositAccountEntity.getId());
			history.setWBalance(withdrawAccountEntity.getBalance());
			history.setDBalance(depositAccountEntity.getBalance());
			
			int resultRowCount = historyRepository.insert(history);
			if(resultRowCount != 1) {
				throw new CustomRestfulException("정상 처리 되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	/**
	 * 단일 계좌 정보 검색
	 * @param id (계좌 PK)
	 * @return Account Entity
	 */
	public Account readAccount(Integer id) {
		// 계좌 존재 여부 확인
		Account accountEntity = accountRepository.findById(id);
		
		if(accountEntity == null) {
			throw new CustomRestfulException("해당 계좌를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		
		return accountEntity;
	}

	/**
	 * 단일 계좌에 대한 거래 내역 검색
	 * @param type = [all, deposit, withdraw]
	 * @param id (account pk)
	 * @return History 거래 내역 (DTO)
	 */
	public List<HistoryDto> readHistoryListByAccount(Integer id, String type) {
		
		List<HistoryDto> historyList = historyRepository.findByHistoryType(id, type);
		
		return historyList;
	}
}
