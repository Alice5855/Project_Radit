package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // ê³„ì¸µ êµ¬ì¡°?? business ?ì—­?? ?´ë‹¹?˜ëŠ” ê°ì²´?„ì„ ëª…ì‹œ
@AllArgsConstructor // ëª¨ë“  parameterë¥? ?´ìš©?˜ëŠ” ?ì„±?ë? ?ë™ ?ì„±
public class BoardServiceImpl implements BoardService {

	// Spring 4.3 ?´ìƒ?ì„œ?? ?¨ì¼ parameterë¥? ê°–ëŠ” ?ì„±?ì˜ ê²½ìš° ?ë™ ì²˜ë¦¬??
	// (Parameterë¥? ?ë™ ì£¼ì…)
	// @Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	// tbl_board?? ê²Œì‹œê¸€ê³? tbl_attach?? file uploadê°€ ?¨ê»˜ ?´ë£¨?´ì ¸?? ?˜ê¸° ?Œë¬¸??
	// Transactional??
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("ê¸€?‘ì„±~~~~?‹ã…‹?‹ã…‹?‹ã…‹?‹ã…‹?‹ã…‹?‹ã…‹?‹ã…‹?‹ã…‹?‹ã…‹ ===== to " + board);
		
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		
		board.getAttachList().forEach(attach -> {
			attach.setB_number(board.getB_number());
			attachMapper.insert(attach);
		});

		
//		BoardAttachVO boardAttachVO = new BoardAttachVO();
//		boardAttachVO = attachMapper.findByB_number(board.getB_number());
		
		
		mapper.setBoardImage(board.getB_number());
	}

	@Override
	public BoardVO get(Long b_number) {
		log.info("get ===== " + b_number + " from board");
		return mapper.read(b_number);
	}

	// ì²¨ë? fileê³? ê²Œì‹œê¸€?? ?˜ì •?? ?¨ê»˜ ?´ë£¨?´ì??„ë¡ Transactional ?ìš©
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		log.info("Modify ===== Modify entry " + board);
		
		attachMapper.deleteAll(board.getB_number());
		boolean modifyResult = mapper.update(board) == 1;
		if (modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setB_number(board.getB_number());
				attachMapper.insert(attach);
			});
		}
		return modifyResult;
		// ì²¨ë?file?€ ?˜ì •?? ?„ë‹Œ, ê¸°ì¡´?? file dataë¥? ?? œ?˜ê³  ?ˆë¡œ?? file?? upload
		// ?˜ëŠ” ?ìœ¼ë¡? ?˜í–‰?œë‹¤
		
		// return mapper.update(board) == 1;
		// ?˜ì •?? ?•ìƒ?ìœ¼ë¡? ?´ë£¨?? ì§€ë©? true ê°’ì´ return??
		// (mapper.update()?ì„œ 1?? ë°˜í™˜??)
	}

	// ê²Œì‹œê¸€ê³? file?? ê°™ì´ ?? œ?˜ë„ë¡? Transaction ?ìš©
	@Transactional
	@Override
	public boolean remove(Long b_number) {
		log.info("remove ===== Remove entry " + b_number);
		attachMapper.deleteAll(b_number);
		// ì²¨ë??? file ?¼ê´„ ?? œ
		return mapper.delete(b_number) == 1;
		// ?˜ì •?? ?•ìƒ?ìœ¼ë¡? ?´ë£¨?? ì§€ë©? true ê°’ì´ return??
		// (mapper.delete()?ì„œ 1?? ë°˜í™˜??)
	}

	/*
	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList ===== Entry List from board" + cri);
		return mapper.getList();
	}
	*/
	
	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList ===== Entry List from board with paging " + cri);
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("getTotalCount ===== " + cri);
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long b_number) {
		log.info("get Attach list in ===== [b_number]" + b_number);
		return attachMapper.findByB_number(b_number);
	}

	@Override
	public String getU_nameFromU_Email(String u_email) {
		log.info("get U_name from U_email");
		return mapper.getU_nameFromU_Email(u_email);
	}

	@Override
	public void setBoardImage(Long b_number, String image) {
		mapper.setBoardImage(b_number, image);
	}
	
	@Override
	public String getU_nameFromU_Email(String u_email) {
		log.info("get U_name from U_email");
		return mapper.getU_nameFromU_Email(u_email);
	}

	@Override
	public void setBoardImage(Long b_number) {
		mapper.setBoardImage(b_number);
	}
	
}
