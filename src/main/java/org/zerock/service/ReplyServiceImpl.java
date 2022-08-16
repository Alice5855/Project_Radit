package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper bMapper;
	// replycnt column 추가 된 후 추가된 code

	// replycnt column 추가 후 댓글 등록 시 replycnt를 update할 수 있도록 트랜잭션 처리
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		log.info("Reply register ===== " + vo);
		
		bMapper.updateReplyCnt(vo.getBno(), 1);
		// replycnt 칼럼 추가된 후 추가된 code
		
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		log.info("Reply get ===== " + rno);
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		log.info("Reply modify ===== " + vo);
		return mapper.update(vo);
	}

	@Transactional
	@Override
	public int remove(Long rno) {
		log.info("Reply remove ===== " + rno);
		
		ReplyVO vo = mapper.read(rno);
		// replycnt 칼럼 추가된 후 추가된 code
		bMapper.updateReplyCnt(vo.getBno(), -1);
		// replycnt 칼럼 추가된 후 추가된 code
		
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		log.info("Reply getList ===== (from) " + bno);
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}
	
}
