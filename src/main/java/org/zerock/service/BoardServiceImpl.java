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
@Service // 계층 구조상 business 영역을 담당하는 객체임을 명시
@AllArgsConstructor // 모든 parameter를 이용하는 생성자를 자동 생성
public class BoardServiceImpl implements BoardService {

	// Spring 4.3 이상에서는 단일 parameter를 갖는 생성자의 경우 자동 처리됨
	// (Parameter를 자동 주입)
	// @Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	// tbl_board에 게시글과 tbl_attach에 file upload가 함께 이루어져야 하기 때문에
	// Transactional화
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("registered ===== to " + board);
		mapper.insertSelectKey(board);
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("get ===== " + bno + " from board");
		return mapper.read(bno);
	}

	// 첨부 file과 게시글의 수정이 함께 이루어지도록 Transactional 적용
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		log.info("Modify ===== Modify entry " + board);
		
		attachMapper.deleteAll(board.getBno());
		boolean modifyResult = mapper.update(board) == 1;
		if (modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach -> {
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
		return modifyResult;
		// 첨부file은 수정이 아닌, 기존의 file data를 삭제하고 새로운 file을 upload
		// 하는 식으로 수행된다
		
		// return mapper.update(board) == 1;
		// 수정이 정상적으로 이루어 지면 true 값이 return됨
		// (mapper.update()에서 1을 반환함)
	}

	// 게시글과 file이 같이 삭제되도록 Transaction 적용
	@Transactional
	@Override
	public boolean remove(Long bno) {
		log.info("remove ===== Remove entry " + bno);
		attachMapper.deleteAll(bno);
		// 첨부된 file 일괄 삭제
		return mapper.delete(bno) == 1;
		// 수정이 정상적으로 이루어 지면 true 값이 return됨
		// (mapper.delete()에서 1을 반환함)
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
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("getTotalCount ===== " + cri);
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("get Attach list in ===== [bno]" + bno);
		return attachMapper.findByBno(bno);
	}

}
