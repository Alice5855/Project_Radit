package org.zerock.mapper;

import java.util.List;

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
public class BoardMapperTests {
	
	// @Setter는 setter 메서드를 생성해 주는 역할을 합니다. 
	// 코드는 특이하게도 JDK 버전에 따라 차이가 있습니다
	// from JDK8：
	// @Setter(onMethod_ = {@AnnotationsGohere})
	// note the underscore after onMethod.
	@Setter (onMethod_ = @Autowired)
	private BoardMapper mapper;
	/*
	@Test
	public void testGetList() {
		try {
			mapper.getList().forEach(board -> log.info(board));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetList2() {
		try {
			log.info("=== GetList2() method called ==="); 
			mapper.getList().forEach(board -> log.info(board));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	*/
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setU_email("test@test.com");
		board.setB_title("First new title");
		board.setB_text("First new context");
		
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setU_email("test@test.com");
		board.setB_title("First new title with Select Key");
		board.setB_text("First new context with Select Key");
		
		mapper.insertSelectKey(board);
		log.info(board);
	}
	
	@Test
	public void testRead() {
		BoardVO board = mapper.read(12L);
		log.info(board);
	}
	
	@Test
	public void testDelete() {
		log.info("DELETE COUNT : " + mapper.delete(5L));
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setB_number(6L);
		
		board.setB_title("Modified title");
		board.setB_text("Modified context");
		board.setU_email("test@test.com");
		
		int count = mapper.update(board);
		
		log.info("UPDATE COUNT : " + count);
	}

	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		List<BoardVO> list = mapper.getListPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("modified");
		cri.setType("TC");
		
		List<BoardVO> list = mapper.getListPaging(cri);
		list.forEach(board -> log.info(board));
	}
}
