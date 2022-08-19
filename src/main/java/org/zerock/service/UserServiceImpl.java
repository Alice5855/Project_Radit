package org.zerock.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zerock.dao.UserDAO;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@Service // 계층 구조상 business 영역을 담당하는 객체임을 명시
@AllArgsConstructor // 모든 parameter를 이용하는 생성자를 자동 생성
public class UserServiceImpl implements UserService {
	@Resource(name = "userDAO")
	private UserDAO userDAO;
	
	private UserMapper usermapper;
	
	public void regist(UserVO user) {
		log.info(user + " 로그 찍어보기 ");
		usermapper.regist(user);;
		
	
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

	@Override
	public UserVO retrieveSessionInfo(String u_Email) {
		
		return userDAO.retrieveSessionInfo(u_Email);
	}

	@Override
	public void updateUserConnectedTime(Map<String, String> boardParam) {
		// TODO Auto-generated method stub
		
	}





}
