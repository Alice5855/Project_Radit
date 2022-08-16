package org.zerock.mapper;

import static org.junit.Assert.fail;

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
	
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("First new title");
		board.setContent("First new context");
		board.setWriter("Randolph");
		
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("First new title with Select Key");
		board.setContent("First new context with Select Key");
		board.setWriter("Randolph");
		
		mapper.insertSelectKey(board);
		log.info(board);
	}
	
	@Test
	public void testRead() {
		BoardVO board = mapper.read(7L);
		log.info(board);
	}
	
	@Test
	public void testDelete() {
		log.info("DELETE COUNT : " + mapper.delete(8L));
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		// 실행 전 bno 유효성 검사
		board.setBno(5L);
		
		board.setTitle("First modified title");
		board.setContent("First modified context");
		board.setWriter("Carter");
		
		int count = mapper.update(board);
		
		log.info("UPDATE COUNT : " + count);
	}

	@Test
	public void testPaging() {
		Criteria cri = new Criteria(3, 10);
		// you can use setPageNum(), amount() method
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
	// Criteria의 keyword와 type을 지정하여 검색이 되는지 확인
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		// Criteria의 parameter가 없는 경우 기본값인 pagenum 1, amount 10
		cri.setKeyword("test");
		// cri.setType("");
		// page 337. type이 공란인 경우 검색조건이 없게 됨(검색되지 않음)
		// cri.setType("T");
		// page 337. 단일 검색 (title)
		cri.setType("TC");
		// page 338. 다중 검색 (title or content)
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
}
