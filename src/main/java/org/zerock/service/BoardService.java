package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board);
	
	// bno값으�? ?�정 게시�? ?�보�? 가?�옴
	public BoardVO get(Long b_number);
	
	public String getU_nameFromU_Email(String u_email);
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long b_number);
	
	// ?�체 게시�? 리스?��? 가?�옴
	// public List<BoardVO> getList();
	
	public List<BoardVO> getList(Criteria cri);
	
	// Page 323 getTotal() method ?�의
	public int getTotal(Criteria cri);
	
	// 첨�? ?�일?? 불러?�기 ?�한 List
	public List<BoardAttachVO> getAttachList(Long b_number);
	
	public void setBoardImage(Long b_number, String image);
	public String getU_nameFromU_Email(String u_email);
	public void setBoardImage(Long b_number);
}
