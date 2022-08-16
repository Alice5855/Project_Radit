package org.zerock.mapper;

import org.zerock.domain.UserVO;

public interface UserMapper {

	public void insert(UserVO user);
	
	public int delete(String u_Email);
	
	public int update(UserVO user);
}
