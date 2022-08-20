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
@Service // 계층 구조?? business ?�역?? ?�당?�는 객체?�을 명시
@AllArgsConstructor // 모든 parameter�? ?�용?�는 ?�성?��? ?�동 ?�성
public class BoardServiceImpl implements BoardService {

	// Spring 4.3 ?�상?�서?? ?�일 parameter�? 갖는 ?�성?�의 경우 ?�동 처리??
	// (Parameter�? ?�동 주입)
	// @Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	// tbl_board?? 게시글�? tbl_attach?? file upload가 ?�께 ?�루?�져?? ?�기 ?�문??
	// Transactional??
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("글?�성~~~~?�ㅋ?�ㅋ?�ㅋ?�ㅋ?�ㅋ?�ㅋ?�ㅋ?�ㅋ?�ㅋ ===== to " + board);
		
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

	// 첨�? file�? 게시글?? ?�정?? ?�께 ?�루?��??�록 Transactional ?�용
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
		// 첨�?file?� ?�정?? ?�닌, 기존?? file data�? ??��?�고 ?�로?? file?? upload
		// ?�는 ?�으�? ?�행?�다
		
		// return mapper.update(board) == 1;
		// ?�정?? ?�상?�으�? ?�루?? 지�? true 값이 return??
		// (mapper.update()?�서 1?? 반환??)
	}

	// 게시글�? file?? 같이 ??��?�도�? Transaction ?�용
	@Transactional
	@Override
	public boolean remove(Long b_number) {
		log.info("remove ===== Remove entry " + b_number);
		attachMapper.deleteAll(b_number);
		// 첨�??? file ?�괄 ??��
		return mapper.delete(b_number) == 1;
		// ?�정?? ?�상?�으�? ?�루?? 지�? true 값이 return??
		// (mapper.delete()?�서 1?? 반환??)
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
