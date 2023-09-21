package com.tencoding.bank.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tencoding.bank.dto.DepositFormDto;
import com.tencoding.bank.dto.HistoryDto;
import com.tencoding.bank.dto.SaveFormDto;
import com.tencoding.bank.dto.TransferFormDto;
import com.tencoding.bank.dto.WithrawFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.handler.exception.UnAuthorizedException;
import com.tencoding.bank.repository.model.Account;
import com.tencoding.bank.repository.model.History;
import com.tencoding.bank.repository.model.User;
import com.tencoding.bank.service.AccountService;
import com.tencoding.bank.utills.Define;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private AccountService accountService;
	
	// 계좌목록 페이지
	// http://localhost:80/account/
	// http://localhost:80/account/list
	// 둘다 가능하게 설정
	@GetMapping({"/list", ""})
	public String list(Model model) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		
		if (user == null) {
			throw new UnAuthorizedException("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		List<Account> accountList = accountService.readAccountList(user.getId());
		
		if (accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("principal", user);
			model.addAttribute("accountList", accountList);
		}
		
		return "account/list";
	}
	
	/**
	 * 계좌 생성 페이지 이동
	 * @return account/list.jsp 페이지 이동
	 */
	// 계좌 생성 페이지
	// http://localhost:80/account/save
	@GetMapping("/save")
	public String save() {
		// 1. 인증 여부 확인
		// 다운캐스팅
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		if(user == null) {
			throw new UnAuthorizedException("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		return "account/save";
	}
	
	/**
	 * 꼐좌 생성 로직 구현
	 * @return
	 */
	@PostMapping("/save")
	public String saveProc(SaveFormDto saveFormDto) {
		// 1. 인증 검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(user == null) {
			throw new UnAuthorizedException("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		// 2. 유효성 검사
		if(saveFormDto.getNumber() == null ||
					saveFormDto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌 번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}

		if(saveFormDto.getPassword() == null ||
					saveFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}

		if(saveFormDto.getBalance() == null || 
					saveFormDto.getBalance() < 0) {
			throw new CustomRestfulException("입금할 금액을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		accountService.createAccount(saveFormDto, user.getId());
		
		return "redirect:/account/list";
	}
	
	// 출금 페이지 
		// http://localhost/account/withdraw
		@GetMapping("/withdraw")
		public String withdraw() {
			// 1. 인증 검사 
			User user = (User)session.getAttribute(Define.PRINCIPAL);
			if(user == null) {
				throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
			} 
			return "account/withdraw";
		}
		// body-> String --> amount=1000&wAccountId=10&/...
		@PostMapping("/withdraw")
		public String withdrawProc(WithrawFormDto withdrawFormDto) {
			// 1. 인증 검사 
			User user = (User)session.getAttribute(Define.PRINCIPAL);
			if(user == null) {
				throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
			} 
			// 2. 유효성 검사 
			if(withdrawFormDto.getAmount() == null) {
				throw new CustomRestfulException("금액을 입력하시오", HttpStatus.BAD_REQUEST);		
			}
			if(withdrawFormDto.getAmount() <= 0) {
				throw new CustomRestfulException("0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
			}
			if(withdrawFormDto.getWAccountNumber() == null || 
					withdrawFormDto.getWAccountNumber().isEmpty()) {
				throw new CustomRestfulException("출금 계좌 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
			}
			if(withdrawFormDto.getWAccountPassword() == null || 
					withdrawFormDto.getWAccountPassword().isEmpty()) {
				throw new CustomRestfulException("출금 계좌 비밀 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
			}
			
			accountService.updateAccountWithdarw(withdrawFormDto, user.getId());
			
			return "redirect:/account/list";
		}

		// 입금 페이지 
		// http://localhost/account/deposit
		@GetMapping("/deposit")
		public String deposit() {
			// 1. 인증 검사 
			User user = (User)session.getAttribute(Define.PRINCIPAL);
			if(user == null) {
				throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
			} 
			return "account/deposit";
		}
		
		@PostMapping("/deposit")
		public String depositProc(DepositFormDto depositFormDto) {
			// 1. 인증 검사 
			User user = (User)session.getAttribute(Define.PRINCIPAL);
			if(user == null) {
				throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
			} 
			// 2. 유효성 검사 
			if(depositFormDto.getAmount() == null) {
				throw new CustomRestfulException("금액을 입력해 주세요", HttpStatus.BAD_REQUEST);
			}
			
			if(depositFormDto.getAmount() <= 0) {
				throw new CustomRestfulException("0원 이하에 금액을 입력할 수 없습니다.", HttpStatus.BAD_REQUEST);
			}
			
			if(depositFormDto.getDAccountNumber() == null || 
					depositFormDto.getDAccountNumber().isEmpty()) {
				throw new CustomRestfulException("계좌 번호를 입력하세요", HttpStatus.BAD_REQUEST);
			}
				
			
			// 3. 서비스 호출 
			accountService.updateAccountDeposit(depositFormDto);
			
			
			return "redirect:/account/list";
		}
	
		// 이체 페이지 
		// http://localhost/account/transfer
		@GetMapping("/transfer")
		public String transfer() {
			// 1. 인증 검사 
			User user = (User)session.getAttribute(Define.PRINCIPAL);
			if(user == null) {
				throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
			} 		
			return "account/transfer";
		}
		
		// 1. 출금 계좌 번호 입력 여부 확인 
		// 2. 입금 계좌 변호 입력 여부 확인 
		// 3. 출금 계좌 비밀번호 입력 여부 확인 
		// 4. 이체 금액 0원 이상 입력 여부 확인	
		@PostMapping("/transfer")
		public String transferProc(TransferFormDto transferFormDto) {
			// 1. 인증 검사 
			User user = (User)session.getAttribute(Define.PRINCIPAL);
			if(user == null) {
				throw new UnAuthorizedException("로그인 먼저 해요", HttpStatus.UNAUTHORIZED);
			} 		
			// 2. 유효성 검사
			if(transferFormDto.getWAccountNumber() == null || 
					transferFormDto.getWAccountNumber().isEmpty()) {
				throw new CustomRestfulException("출금 계좌 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
			}
			
			if(transferFormDto.getDAccountNumber() == null || 
					transferFormDto.getDAccountNumber().isEmpty()) {
				throw new CustomRestfulException("입금 계좌 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
			}
			
			if(transferFormDto.getWAccountPassword() == null || 
					transferFormDto.getWAccountPassword().isEmpty()) {
				throw new CustomRestfulException("출금 계좌 번호를 입력 하시오", HttpStatus.BAD_REQUEST);
			}
			
			if(transferFormDto.getAmount() <= 0) {
				throw new CustomRestfulException("이체 금액이 0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
			}
			
			// 3. 서비스 호출
			accountService.updateAccountTransfer(transferFormDto, user.getId());
			
			return "redirect:/account/list";
		}
	
	// TODO: 수정필요!
	// 상세 보기 페이지
	// http://localhost:80/account/detail/계좌고유번호
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id,
						@RequestParam(name="type", 
									  defaultValue = "all", 
									  required = false) String type, Model model) {
		// TODO - 주소 설계 추가하기
		// 1. 인증 검사
		User user = (User)session.getAttribute(Define.PRINCIPAL);
				
		if(user == null) {
			throw new UnAuthorizedException("로그인이 필요한 작업입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		// 서비스 호출
		Account account = accountService.readAccount(id);
		List<HistoryDto> historyList = accountService.readHistoryListByAccount(id, type);
		// 필요한 정보들
		// Account
		// List<History>
		model.addAttribute("principal", user);
		model.addAttribute("account", account);
		model.addAttribute("historyList", historyList);
		
		
		return "account/detail";
	}
}
