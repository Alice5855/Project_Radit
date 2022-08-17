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
		log.info("시작");
		UserVO user = new UserVO();
		log.info("중간");
		user.setU_Email("권한테스트에미일");
		user.setU_Name("오후9시");
		user.setU_pw("ㅋㅋㅋ");
		user.setU_Address("큭크긐ㄱ");
		user.setU_gender("키키킥");
		user.setU_profile_path("junit이미지0817");
		userService.regist(user);
		log.info(user + " 로그 찍어보기 ");
	}

//	@Test
//	public void testGet() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testModify() {
//		UserVO user = userService.getINFO("0817오후1시.net");
//		if (user == null) {
//			return;
//		} else {
//			user.setU_Address("0817오후1시 2022동");;
//		}
//		
//		log.info("수정 결과 : " + userService.update(user));
//		
//		
//	}
//
	@Test
	public void testRemove() {
		log.info("삭제 결과 : " + userService.deleteAccount("test1@test.com"));
	}



}
