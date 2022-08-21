package org.zerock.service;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.UserVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
   "file:src/main/webapp/WEB-INF/spring/root-context.xml",
   "file:src/main/webapp/WEB-INF/spring/security-context.xml"
   })
@Log4j
public class UserServiceImplTest {
   
   @Setter(onMethod_ = @Autowired)
   private PasswordEncoder pwEncoder;
   
   @Setter(onMethod_ = @Autowired)
   private DataSource ds;
   
   @Setter(onMethod_ = @Autowired)
   private UserService userService;
   
   
   
   
   @Test
   public void testRegister() {
//      log.info("시작");
      UserVO user = new UserVO();
//      log.info("중간");
      user.setU_Email("test2@mailhost.com");
      user.setU_Name("test2");
      user.setU_pw("test2");
      user.setU_Address("Apache");
      user.setU_gender("Attack-Helicopter");
      user.setU_profile_path("SadPepe.gif");
      
      log.info(user.getU_Email());
      
      userService.regist(user);
      log.info(user + " 로그 찍어보기 ");
   }

//   @Test
//   public void testGet() {
//      fail("Not yet implemented");
//   }

//   @Test
//   public void testModify() {
//      UserVO user = userService.getINFO("20220818@test.com");
//      if (user == null) {
//         return;
//      } else {
//         user.setU_Address("테스트로 바뀌는 주소");;
//      }
//      
//      log.info("수정 결과 : " + userService.update(user));
//      
//      
//   }

//   @Test
//   public void testdeleteAccount() {
//      try {
//         userService.deleteAccount("junitTest@test.com");
//      } catch (Exception e) {
//         fail(e.getMessage());
//      } 
//      
//      
//   }
   
   
   
   
   



}