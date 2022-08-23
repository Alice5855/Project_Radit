package org.zerock.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserVO implements UserDetails {
   
	private static final long serialVersionUID = 1L;
	private String u_Email;
	private String u_Name;
	private String u_pw;
	private String u_Address;
	private String u_gender;
	private String u_profile_path;
	
   //권한
   private AuthVO u_Auth;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      ArrayList<GrantedAuthority> auth  = new ArrayList<GrantedAuthority>();
      auth.add(new SimpleGrantedAuthority(u_Auth.getU_Auth()));
      return auth;
   }
   
   
   public String getName() {
      return u_Name;
   }
   
   public void setName(String name) {
      u_Name = name;
   }
   
   public void setEmail(String email) {
      u_Email = email;
   }
   
   @Override
   public String getPassword() {
      return u_pw;
   }
   
   @Override
   public String getUsername() {
      return u_Email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

}