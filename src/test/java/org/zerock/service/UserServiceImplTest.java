package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.UserVO;

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
		log.info("시작");
		UserVO user = new UserVO();
		log.info("중간");
		user.setU_Email("junitTest@test.com");
		user.setU_Name("Test00");
		user.setU_pw("junit0816");
		user.setU_Address("junit주소정보");
		user.setU_gender("성별정보");
		user.setU_profile_path("junit이미지경로");
		userService.register(user);
		log.info(user + " 로그 찍어보기 ");
	}

//	@Test
//	public void testGet() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testModify() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemove() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUserServiceImpl() {
//		fail("Not yet implemented");
//	}

}
