package org.zerock.domain;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.domain.UserVO;



public class UserDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean logincheck(String u_Email, String u_pw){
		boolean check = false;
		String email = null;
		HashMap<String, String> hm = new HashMap<String, String>();

		hm.put("u_Email", u_Email);
		hm.put("u_pw", u_pw);

		email = sqlSession.selectOne("user.logincheck", hm);
		if(email != null){
			check = true;
		}
		return check;
	}
	
	
	
	
	
	public UserVO retrieveSessionInfo(String u_Email){
		return sqlSession.selectOne("user.retrieveSessionInfo", u_Email);
	}
}
