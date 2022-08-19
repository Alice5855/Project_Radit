package org.zerock.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.UserVO;
import org.zerock.mapper.UserMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class UserServiceImplTest {
	
	@Setter(onMethod_ = @Autowired)
	private UserService userService;
	
	
	
	@Test
	public void testRegister() {
//		log.info("시작");
		UserVO user = new UserVO();
//		log.info("중간");
		user.setU_Email("0819@test.com");
		user.setU_Name("오전22시");
		user.setU_pw("test");
		user.setU_Address("대충 주소");
		user.setU_gender("남성");
		user.setU_profile_path("대충 이미지 uuid");
		userService.regist(user);
		log.info(user + " 로그 찍어보기 ");
	}

//	@Test
//	public void testGet() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testModify() {
//		UserVO user = userService.getINFO("20220818@test.com");
//		if (user == null) {
//			return;
//		} else {
//			user.setU_Address("테스트로 바뀌는 주소");;
//		}
//		
//		log.info("수정 결과 : " + userService.update(user));
//		
//		
//	}

	@Test
	public void testdeleteAccount() {
		try {
			userService.deleteAccount("junitTest@test.com");
		} catch (Exception e) {
			fail(e.getMessage());
		} 
		
		
	}
	
	
	
	
	



}
