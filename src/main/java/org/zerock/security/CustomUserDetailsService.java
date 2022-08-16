package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;
import org.zerock.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
	// MemberMapper type의 instance를 주입받아 기능을 구현하는 UserDetailsService
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load user by UserName ===== " + username);
		
		// org.zerock.security.domain.CustomUser class에서 받아옴
		MemberVO vo = memberMapper.read(username);
		log.warn("Queried by member mapper ===== " + vo);
		
		return vo == null ? null : new CustomUser(vo);
		// vo가 null인 경우 null을 반환하고 null이 아닌 경우 vo(Param username의 data
		// 를 mapper에서 읽어온 MemberVO)를 CustomUser객체 형으로 return
	}
	
}
