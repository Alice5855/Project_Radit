package org.zerock.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
	@Test
	public void testExist() {
		try {
			log.info(service);
			assertNotNull(service);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	// 등록 작업 register() method test
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("Newly entired Title");
		board.setContent("Newly entried Context");
		board.setWriter("Carter");
		
		service.register(board);
		log.info("Newly entried number : " + board.getBno());
	}
	
	// board 글 목록 가져오는 getList() method test
	@Test
	public void testGetList() {
		// service.getList().forEach(board -> log.info(board));
		// ㄴ> before make Criteria
		service.getList(new Criteria(2, 10)).forEach(board -> log.info(board));
	}
	
	@Test
	public void testGet() {
		long testbno = 13L;
		log.info(service.get(testbno));
	}
	
	@Test
	public void testRemove() {
		// bno 유효성 검사
		log.info("REMOVE RESULT : " + service.remove(2L));
		// remove() method가 게시물을 성공적으로 삭제했을 때 true를 반환
	}
	
	@Test
	public void testModify() {
		BoardVO board = service.get(1L);
		if (board == null) {
			return;
		} else {
			board.setTitle("Newly modified title");
		}
		log.info("MODIFY RESULT : " + service.modify(board));
		// modify() method가 게시물을 성공적으로 변경했을 때 true를 반환
	}
}
