package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // 계층 구조상 business 영역을 담당하는 객체임을 명시
@AllArgsConstructor // 모든 parameter를 이용하는 생성자를 자동 생성
public class UserServiceImpl implements UserService {
	
	private UserMapper usermapper;
	
	public void register(UserVO user) {
		log.info(user + " 로그 찍어보기 ");
		usermapper.insert(user);
		
	
	}
//
//	@Override
//	public UserVO get(String u_Email) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public boolean modify(UserVO user) {
		log.info(user + "수정하기");
		boolean modifyResult =  usermapper.update(user) == 1;
		return modifyResult;
	}

	@Override
	public boolean remove(String u_Email) {
		log.info(u_Email + "삭제수구븜");
		
		return usermapper.delete(u_Email) == 1;
	}



}
