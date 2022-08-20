package org.zerock.controller;

import static org.junit.Assert.fail;

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
// web 연계 추가. ServletContext를 이용하기 위함
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
	})
// Web 연계 추가. WebApplicationContext를 사용하기 위함
@Log4j
public class BoardControllerTests {
	// Junit의 test 기능으로 server를 구동하지 않고도 code를 테스트 할 수 있음.
	// 테스트를 할 때마다 server를 재시작 할 필요가 없어 잦은 test에 용이

	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	// Mock up MVC. 가상의 Model, View, Controller를 제공하여 테스트 할 수 있음
	
	// @Before : 모든 test 전에 매번 실행 되는 method
	@Before
	@Test
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	// 예제에서는 MockMvcBuilders를 활용해 Mock mvc를 생성하는 과정을 setup()
	// method로 정의하고 있다. Test에서 사용할 MockMvc를 재정의 해주는 것
	
	// MockMvcRequestBuilders의 get() method로 get 방식 지정
	@Test
	public void testList() {
		try {
			log.info(
					mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
					.andReturn().getModelAndView().getModelMap()
					);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	// MockMvcRequestBuilders의 post() method로 post 방식 지정
	// .param() method로 각 parameter들 전달
	@Test
	public void testRegister() {
		String resultPage;
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
					.param("b_title", "Newly entried title")
					.param("b_text", "Newly entried content")
					.param("u_email", "test@test.com")
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	// 특정 게시물 조회 시 bno parameter가 필요하기 때문에 param() method 활용
	@Test
	public void testGet() {
		try {
			log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
					.param("b_number", "5")).andReturn()
					.getModelAndView().getModelMap());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testModify() {
		String resultPage;
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
					.param("b_number", "7")
					.param("b_title", "Newly entried title via controller")
					.param("b_text", "Newly entried content via controller")
					.param("u_email", "test@test.com")
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testRemove() {
		// MockMvc를 이용하여 parameter를 전달 할 때에는 문자열로만 처리할 수 있음
		// bno는 원래 long type이지만 "3"으로 전달함
		String resultPage;
		try {
			resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
					.param("b_number", "4")
					).andReturn().getModelAndView().getViewName();
			log.info(resultPage);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	// fk인 u_email이 존재하기 때문에 test를 위해서는 u_email을 Controller에서 받아야함
	
	@Test
	public void testPaging() {
		try {
			log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
					.param("pageNum", "1")
					.param("amount", "10"))
					.andReturn().getModelAndView().getModelMap()
					);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
