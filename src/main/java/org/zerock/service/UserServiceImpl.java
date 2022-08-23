package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.domain.UserVO;
import org.zerock.mapper.AuthMapper;
import org.zerock.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 계층 구조상 business 영역을 담당하는 객체임을 명시
@AllArgsConstructor // 모든 parameter를 이용하는 생성자를 자동 생성
public class UserServiceImpl implements UserService {
	
	private UserMapper usermapper;
	private AuthMapper authmapper;
	@Setter(onMethod_ = @Autowired)
	private BCryptPasswordEncoder pwEncoder;
	
	public void regist(UserVO user) {
		log.info("USER ===== " + user);
		String enpw = user.getU_pw();
		user.setU_pw(pwEncoder.encode(enpw));
		usermapper.regist(user);
		
		try {
			usermapper.read(user.getU_Email());
			authmapper.authInsert(user.getU_Email());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserVO getINFO(String u_Email) {
		log.info("get=====" + u_Email );
		return usermapper.read(u_Email) ;
	}

	@Override
	public boolean update(UserVO user) {
		log.info(user + "수정하기");
		String enpw = user.getU_pw();
		user.setU_pw(pwEncoder.encode(enpw));
		boolean modifyResult =  usermapper.update(user) == 1;
		return modifyResult;
	}

	@Override
	public boolean deleteAccount(String u_Email) {
		log.info(u_Email + "삭제수구븜");
			
		return usermapper.deleteAccount(u_Email) == 1; 
		
	}


}
