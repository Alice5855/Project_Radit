package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	private Long[] bnoArr = {524315L, 524304L, 524308L, 524313L, 524311L};
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	// ReplyMapper 객체가 정상 사용이 가능한지 test
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			// IntStream.rangeClosed(1,10)로  1~10까지의 값을 갖는 배열변수 객체 생성
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i%5]);
			/* i(1~10)을 5로 나눈 나머지 값인 [1,2,3,4,0, 1,2,3,4,0]이 대입되어
			 * bnoArray의 index1, 2, 3, 4, 0을 순서대로 두번씩 가져오게 되는 것
			 */
			vo.setReply("Test Reply" + i);
			vo.setReplyer("Replyer" + i);
			
			mapper.insert(vo);
		});
	}
	
	@Test
	public void testRead() {
		Long targetRno = 5L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}

	@Test
	public void testDelete() {
		Long targetRno = 20L;
		
		mapper.delete(targetRno);
		
		log.info("Reply entry " + targetRno + " deleted");
	}
	
	@Test
	public void testUpdate() {
		Long targetRno = 1L;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Updated reply " + targetRno);
		int count = mapper.update(vo);
		
		log.info("Reply entry " + targetRno + " updated ===== " + count);
	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria(); // default 1page, 10amount
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		// bnoArr : 21st row 0 index
		replies.forEach(reply -> log.info(reply));
	}
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1,10);
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 524313L);
		replies.forEach(reply -> log.info(reply));
	}
}
