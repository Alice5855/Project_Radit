package org.zerock.service;

import org.zerock.domain.UserVO;


public interface UserService {
	
	public void register(UserVO user);
	
	public boolean modify(UserVO user);
	
	public boolean remove(String u_Email);
	
//	public UserVO get(String u_Email);
	

}
