package org.zerock.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;








@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
	})

@Log4j
public class UserControllerTests {
	
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testRegist() {
		String resultPage;
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/regist")
					.param("u_Email", "controller@test.test")
					.param("u_Name", "controllerTest")
					.param("u_pw", "controller1234")
					.param("u_Address", "컨트롤러테스트구행복한동")
					.param("u_gender", "컨트롤러")
					.param("u_profile_path", "컨트롤러이미지경로")
					
					).andReturn().getModelAndView().getViewName();
			
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
			
		}
	}
	
	
	@Test
	public void testModify() {
		String resultPage;
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/update")
					.param("u_Email", "controller@test.test")
					.param("u_Name", "컨트롤러테스트수정이름")
					.param("u_pw", "컨테")
					.param("u_Address", "행복한곳에살아요")
					.param("u_gender", "여성")
					.param("u_profile_path", "대충이미지")
					
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testRemove() {
		String resultPage;
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/deleteAccount")
					.param("u_Email", "controller@test.test")
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAuthUpdate() {
		String resultPage;
		
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/user/Auth")
					.param("u_Email", "20220818@test.com")
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	

}
