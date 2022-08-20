package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.domain.UserVO;
import org.zerock.mapper.AuthMapper;
import org.zerock.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 계층 구조상 business 영역을 담당하는 객체임을 명시
@AllArgsConstructor // 모든 parameter를 이용하는 생성자를 자동 생성
public class UserServiceImpl implements UserService {
	
	private UserMapper usermapper;
	private AuthMapper authmapper;
	
	public void regist(UserVO user) {
		log.info(user + " 로그 찍어보기 ");
		usermapper.regist(user);
		
		try {
			usermapper.read(user.getU_Email());
			authmapper.AuthInsert(user.getU_Email());
			log.info(user.getU_Email() + "이게 안된다고 시발 실화니?");
		} catch (Exception e) {
			// TODO: handle exception
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
		boolean modifyResult =  usermapper.update(user) == 1;
		return modifyResult;
	}

	@Override
	public boolean deleteAccount(String u_Email) {
		log.info(u_Email + "삭제수구븜");
		
		return usermapper.deleteAccount(u_Email) == 1;
	}





}
