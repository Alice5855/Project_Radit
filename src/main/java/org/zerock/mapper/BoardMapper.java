package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	// resources/org.zerock.mapper의 BoardMapper를 사용하기 위해 주석처리됨
	@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	public List<BoardVO> getList2();
	
	// Pagination
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// create(insert) query를 처리하기 위한 method
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	// read(select) query를 처리하기 위한 method
	public BoardVO read(Long bno);
	
	// delete query를 처리하기 위한 method
	public int delete(Long bno);
	
	// update 제목, 내용, 작성자 수정 query를 처리하기 위한 method
	// update 할 때에 수정 시간을 실시간으로 가져와 수정해야함
	// delete method와 마찬가지로 count만 출력하기 위해 int type으로 설정
	public int update(BoardVO board);
	
	// Page 322 getTotalCount() method 추가
	public int getTotalCount(Criteria cri);
	// resources/org.zerock.mapper/BoardMapper.xml에 SQL query 추가
	// 게시물 목록과 전체 data 수를 구하는 기능. 검색 기능을 추가할 때 필요함
	
	// Page 481. replyCnt를 update하는 method
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	// bno와 증감을 검증할 수 있는 amount 변수를 parameter로 받는다.
	// MyBatis의 SQL query에서는 기본적으로는 하나의 param만을 사용하기 때문에 2개 이상일
	// 경우에는 @Param을 사용해야 한다
}
