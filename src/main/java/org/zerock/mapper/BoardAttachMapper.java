package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	public void insert(BoardAttachVO vo);
	public void delete(String uuid);
	public List<BoardAttachVO> findByBno(Long bno);
	// 첨부파일은 수정의 개념이 없기 때문에 CRD만 정의죔
	
	public void deleteAll(Long bno);
	// 첨부 파일 일괄 삭제
	
	// file의 유효성을 검증하는 method(Page600)
	public List<BoardAttachVO> getOldFiles();
}
