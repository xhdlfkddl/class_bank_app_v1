package com.tencoding.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.bank.dto.SignInFormDto;
import com.tencoding.bank.dto.SignUpFormDto;
import com.tencoding.bank.handler.exception.CustomRestfulException;
import com.tencoding.bank.repository.interfaces.UserRepository;
import com.tencoding.bank.repository.model.User;

// IoC 대상
// 싱글톤 패턴(= 객체 하나만 메모리에 올라감)
@Service
public class UserService {

	// DAO - 데이터 베이스와 연동
	// 아래 생성자로 만드는 것을 자동으로 해주는 역할
	@Autowired
	private UserRepository userRepository;
	
	// DI - 가지고 오는 것
//	public UserService(UserRepository userRepository) {
//		
//	}
	
	// @Transactional
	// 정상 처리 시 commit(= 반영)
	// 비정상 처리 시 rollback 처리되기 때문에 사용
	@Transactional
	public void signUp(SignUpFormDto signUpFormDto) {
		// 실행되면 userRepository 로 가게 되고
		// userRepository @mapper 되어있기 때문에
		// mapper 패키지 -> user.xml 파일로 들어가 해당 구문을 실행
		int result = userRepository.insert(signUpFormDto);
		
		System.out.println("result: " + result);
		
		if(result != 1) {
			throw new CustomRestfulException("회원가입 실패", 
						HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//
	public User signIn(SignInFormDto signInFormDto) {
		User userEntity = userRepository.findByUsernameAndPassword(signInFormDto);
		if(userEntity == null) {
			throw new CustomRestfulException("아이디 또는 비밀번호가 틀렸습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return userEntity;
	}
	
}
