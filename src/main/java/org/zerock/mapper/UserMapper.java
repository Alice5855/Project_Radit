package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.UserVO;

public interface UserMapper {

   public void regist(UserVO user);
   
   public int deleteAccount(String u_Email);
   
   public int update(UserVO user);
   
   public List<UserVO> getINFO();
   
   public UserVO read(String u_Email);
   
}