package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	public List<BoardVO> getListPaging(Criteria cri);
	public void insert(BoardVO board);
	public void insertSelectKey(BoardVO board);
	public BoardVO read(Long b_number);
	public int delete(Long b_number);
	public int update(BoardVO board);
	public int getTotalCount(Criteria cri);
	
	public String getU_nameFromU_Email(String u_email);
	public void setBoardImage(Long b_number, String image);
	public void setBoardImage(Long b_number);
}
