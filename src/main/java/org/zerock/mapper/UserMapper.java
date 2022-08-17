package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.UserVO;

public interface UserMapper {

	public void insert(UserVO user);
	
	public int delete(String u_Email);
	
	public int update(UserVO user);
	
	public List<UserVO> getList();
	
	public UserVO read(String u_Email);
}
