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
	
	
	
//	@Test
//	public void testRegister() {
//		log.info("시작");
//		UserVO user = new UserVO();
//		log.info("중간");
//		user.setU_Email("junit@test.net");
//		user.setU_Name("Test123");
//		user.setU_pw("junit0817");
//		user.setU_Address("junit0817");
//		user.setU_gender("0817");
//		user.setU_profile_path("junit이미지0817");
//		userService.register(user);
//		log.info(user + " 로그 찍어보기 ");
//	}

//	@Test
//	public void testGet() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testModify() {
		UserVO user = userService.get("junit@test.net");
		if (user == null) {
			return;
		} else {
			user.setU_Address("junit수정테스트0817_두번째");;
		}
		
		log.info("수정 결과 : " + userService.modify(user));
		
		
	}

	@Test
	public void testRemove() {
		log.info("삭제 결과 : " + userService.remove("testEmail"));
	}



}
