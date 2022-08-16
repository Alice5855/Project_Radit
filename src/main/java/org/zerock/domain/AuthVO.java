package org.zerock.domain;

import lombok.Data;

@Data
public class AuthVO {
	// tbl_member_auth table 정의
	private String userid;
	private String auth;
}
