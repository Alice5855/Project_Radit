package org.zerock.domain;

import lombok.Data;

@Data
public class UserVO {
	
	private String u_Email;
	private String u_Name;
	private String u_pw;
	private String u_Address;
	private String u_gender;
	private String u_profile_path;
	
	//권한
	private String u_Auth;

}
