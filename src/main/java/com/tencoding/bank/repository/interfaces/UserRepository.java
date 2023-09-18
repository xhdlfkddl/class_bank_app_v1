package com.tencoding.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tencoding.bank.dto.SignInFormDto;
import com.tencoding.bank.dto.SignUpFormDto;
import com.tencoding.bank.repository.model.User;

// xml 파일과 사용할 예정
// ibatis = MyBatis(2.7 ver 이후부터)
@Mapper // Mapper 반드시 기술해주어야함
public interface UserRepository {
	// 성공 시 컬럼이 +1 또는 -1 또는 1개의 컬럼이 return 되기 때문에 
	// return 타입이 int임  
	
	// 매개변수 수정 User -> SignupFormDto
	public int insert(SignUpFormDto dto);
	public int updateById(User user);
	public int deleteById(Integer id);
	public User findById(Integer id);
	
	// 관리자 - 회원정보 리스트를 보려면
	public List<User> findAll();
	
	public User findByUsernameAndPassword(SignInFormDto signInFormDto);
}
