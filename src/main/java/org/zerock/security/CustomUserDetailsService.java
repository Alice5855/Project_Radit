package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;
import org.zerock.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
	// MemberMapper type의 instance를 주입받아 기능을 구현하는 UserDetailsService
	@Setter(onMethod_ = @Autowired)
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String u_Email) throws UsernameNotFoundException {
		log.warn("Load user by UserName ===== " + u_Email);
		
		// org.zerock.security.domain.CustomUser class에서 받아옴
		UserVO user = userMapper.read(u_Email);
		log.warn("Queried by member mapper ===== " + user);
		
		return user == null ? null : new CustomUser(user);
		// vo가 null인 경우 null을 반환하고 null이 아닌 경우 vo(Param username의 data
		// 를 mapper에서 읽어온 MemberVO)를 CustomUser객체 형으로 return
	}
	
}
