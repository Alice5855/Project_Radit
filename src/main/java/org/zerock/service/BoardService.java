package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board);
	
	// bnoê°’ìœ¼ë¡? ?¹ì • ê²Œì‹œë¬? ?•ë³´ë¥? ê°€?¸ì˜´
	public BoardVO get(Long b_number);
	
	public String getU_nameFromU_Email(String u_email);
	
	public boolean modify(BoardVO board);
	
	public boolean remove(Long b_number);
	
	// ?„ì²´ ê²Œì‹œë¬? ë¦¬ìŠ¤?¸ë? ê°€?¸ì˜´
	// public List<BoardVO> getList();
	
	public List<BoardVO> getList(Criteria cri);
	
	// Page 323 getTotal() method ?•ì˜
	public int getTotal(Criteria cri);
	
	// ì²¨ë? ?Œì¼?? ë¶ˆëŸ¬?¤ê¸° ?„í•œ List
	public List<BoardAttachVO> getAttachList(Long b_number);
	
	public void setBoardImage(Long b_number, String image);
	public String getU_nameFromU_Email(String u_email);
	public void setBoardImage(Long b_number);
}
