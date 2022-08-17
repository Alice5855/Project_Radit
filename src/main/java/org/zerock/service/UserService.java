package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.domain.UserVO;


public interface UserService {
	
	public void regist(UserVO user);
	
	public boolean update(UserVO user);
	
	public boolean deleteAccount(String u_Email);
	
	public UserVO getINFO(String u_Email);
	

}
