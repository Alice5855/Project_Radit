package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
	// C
	public int insert(ReplyVO vo);
	
	// R
	public ReplyVO read(Long rno);
	
	// U
	public int update(ReplyVO vo);
	// 댓글 수정 시 updateDate를 수정해야함
	
	// D
	public int delete(Long rno);
	
	// List
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	/* page 387 @Param Annotation과 댓글 List 구형 : 댓글 목록의 페이징 처리는
	 * 게시물의 페이징처리와 유사하나 특정한 게시물의 댓글들만을 대상으로 한다는 특성상
	 * 추가로 bno가 필요하게 된다. MyBatis에서 두 개 이상의 Parameter를 전달하기 위해서는
	 * 1] 별도의 객체로 구성하거나 2] Map을 이용하거나 3] @Param 을 이용하여 전달해야함
	 * 가장 간단하게 사용할 수 있는 것이 @Param으로 DB의 column명으로 data를 전달할 수 있도록
	 * 한다
	 * Paging은 Criteria를 활용. 해당 bno를 parameter로 전달하도록 ReplyMapper를 구성
	 * XML을 활용한 data handle시 cri, bno 속성을 사용 가능
	 */
	
	// page 432 : paging 처리를 위하여 특정 bno의 reply count를 위해 method 추가
	public int getCountByBno(Long bno);
}
