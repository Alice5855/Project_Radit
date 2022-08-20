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
@Service // ๊ณ์ธต ๊ตฌ์กฐ?? business ?์ญ?? ?ด๋น?๋ ๊ฐ์ฒด?์ ๋ช์
@AllArgsConstructor // ๋ชจ๋  parameter๋ฅ? ?ด์ฉ?๋ ?์ฑ?๋? ?๋ ?์ฑ
public class BoardServiceImpl implements BoardService {

	// Spring 4.3 ?ด์?์?? ?จ์ผ parameter๋ฅ? ๊ฐ๋ ?์ฑ?์ ๊ฒฝ์ฐ ?๋ ์ฒ๋ฆฌ??
	// (Parameter๋ฅ? ?๋ ์ฃผ์)
	// @Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	// tbl_board?? ๊ฒ์๊ธ๊ณ? tbl_attach?? file upload๊ฐ ?จ๊ป ?ด๋ฃจ?ด์ ธ?? ?๊ธฐ ?๋ฌธ??
	// Transactional??
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("๊ธ?์ฑ~~~~?ใ?ใ?ใ?ใ?ใ?ใ?ใ?ใ?ใ ===== to " + board);
		
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

	// ์ฒจ๋? file๊ณ? ๊ฒ์๊ธ?? ?์ ?? ?จ๊ป ?ด๋ฃจ?ด์??๋ก Transactional ?์ฉ
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
		// ์ฒจ๋?file? ?์ ?? ?๋, ๊ธฐ์กด?? file data๋ฅ? ?? ?๊ณ  ?๋ก?? file?? upload
		// ?๋ ?์ผ๋ก? ?ํ?๋ค
		
		// return mapper.update(board) == 1;
		// ?์ ?? ?์?์ผ๋ก? ?ด๋ฃจ?? ์ง๋ฉ? true ๊ฐ์ด return??
		// (mapper.update()?์ 1?? ๋ฐํ??)
	}

	// ๊ฒ์๊ธ๊ณ? file?? ๊ฐ์ด ?? ?๋๋ก? Transaction ?์ฉ
	@Transactional
	@Override
	public boolean remove(Long b_number) {
		log.info("remove ===== Remove entry " + b_number);
		attachMapper.deleteAll(b_number);
		// ์ฒจ๋??? file ?ผ๊ด ?? 
		return mapper.delete(b_number) == 1;
		// ?์ ?? ?์?์ผ๋ก? ?ด๋ฃจ?? ์ง๋ฉ? true ๊ฐ์ด return??
		// (mapper.delete()?์ 1?? ๋ฐํ??)
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
