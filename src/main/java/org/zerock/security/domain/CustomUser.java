package org.zerock.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerock.domain.MemberVO;
import org.zerock.domain.UserVO;

import lombok.Getter;

@Getter
public class CustomUser extends User {
	// org.springframework.security.core.userdetails.User를 상속하여 User
	// class의 생성자를 사용함 (super())

	private static final long serialVersionUID = 1L;
	
	private UserVO user;
	
	// constructor
	public CustomUser(UserVO user) {
		super(user.getU_Email(), user.getU_pw(), user.getU_Auth().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getU_Auth()))
				.collect(Collectors.toList()));
		this.user = user;
	}
	// MemberVO를 param으로 전달하여 부모 class인 User의 생성자에 맞추기 위해 
	// GrantedAuthority 객체로 stream()과 map()을 활용하여 type 변환
	
	public CustomUser(String u_Email, String u_pw, Collection<? extends GrantedAuthority> authorities) {
		super(u_Email, u_pw, authorities);
	}
}
