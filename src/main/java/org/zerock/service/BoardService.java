package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board);
	
	// bno값으로 특정 게시물 정보를 가져옴
	public BoardVO get(Long b_number);
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long b_number);
	
	// 전체 게시물 리스트를 가져옴
	// public List<BoardVO> getList();
	
	public List<BoardVO> getList(Criteria cri);
	
	// Page 323 getTotal() method 정의
	public int getTotal(Criteria cri);
	
	// 첨부 파일을 불러오기 위한 List
	public List<BoardAttachVO> getAttachList(Long b_number);
	
	public String getU_nameFromU_Email(String u_email);
	public void setBoardImage(Long b_number);

	BoardVO getRaw(Long b_number);
}
