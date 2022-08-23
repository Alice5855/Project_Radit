package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
	// MemberMapper type의 instance를 주입받아 기능을 구현하는 UserDetailsService
	@Setter(onMethod_ = @Autowired)
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String u_Email) throws UsernameNotFoundException {
		UserVO user = userMapper.read(u_Email);
		System.out.println("이거 언제 작동함?");
		if(user == null) {
			
			throw new UsernameNotFoundException(u_Email);
		}
		
		log.info(user);
		return user;
	}
	
}
