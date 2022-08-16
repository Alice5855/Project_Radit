package org.zerock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/replies/")
@AllArgsConstructor
@Log4j
public class ReplyController {
	
	private ReplyService service;
	// Spring 4.3 이상 version에서는 @Setter(onMethod_ = @Autowired) 대신
	// @AllArgsConstructor로 대체할 수 있다
	
	
	// 새 댓글
	// Page729 댓글 등록이 로그인 한 사용자와 일치하는지 검증
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		log.info("ReplyVO ====== " + vo);
		int insertCount = service.register(vo);
		log.info("Reply insert count ===== " + insertCount);
		return insertCount == 1 ? new ResponseEntity<>("Success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/* create()는 post method로 동작한다. consumes와 produces 속성으로 JSON type
	 * 의 data를 text 형태로 반환하도록 한다. 
	 * @RequestBody로 JSON data를 ReplyVO type으로 변환하도록 명시
	 * ReplyServiceImpl의 register() method를 호출하여 추가된 댓글의 수를 count하여
	 * ResponseEntity 객체에서 Reply entry가 정상적으로 생성되었는지를 검사한다.
	 * 
	 * http://localhost:8088/replies/pages/524313/1
	 * YetAnotherRestClient에서 확인, lecture70
	 */
	
	// 목록 확인
	/*
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		log.info("Reply getList ====== [page]" + page + "[bno]" + bno);
		Criteria cri = new Criteria(page, 10);
		log.info("Criteria =====" + cri);
		return new ResponseEntity<>(service.getList(cri, bno),HttpStatus.OK);
	}
	*/
	
	/* Criteria를 이용하여 page parameter를 수집, '/{bno}/{page}'에서의
	 * Variable path인 {page}를 직접 생성하여 ReplyServiceImpl의 getList()
	 * 에 PathVariable로 입력받는 page parameter와 bno parameter를 대입하여
	 * 해당 ReplyVO의 data를 xml, json으로 가져온다
	 * 
	 * http://localhost:8088/replies/pages/524313/1.json
	 */
	
	// page 435 : ReplyPageDTO를 활용하여 paging 처리된 댓글 list 처리
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		log.info("Reply getList with ReplyPageDTO ====== [page]" + page + "[bno]" + bno);
		Criteria cri = new Criteria(page, 10);
		log.info("get Reply List bno ===== " + bno);
		log.info("Criteria =====" + cri);
		return new ResponseEntity<>(service.getListPage(cri, bno),HttpStatus.OK);
	}
	
	// 댓글 조회
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("Reply get ===== [rno]" + rno);
		return new ResponseEntity<>(service.get(rno),HttpStatus.OK);
	}
	// http://localhost:8088/replies/1.json
	
	// 댓글 삭제
	// Page 732 replyer 검증절차를 위한 annotation(PreAuthorize), parameter(vo) 추가
	// ajax에서 전송받은 JSON data를 VO 객체화 시키기 위해 @RequestBody를 적용함
	@PreAuthorize("principal.username == #vo.replyer")
	@DeleteMapping(value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		log.info("ReplyVO ===== " + vo);
		log.info("replyer ===== " + vo.getReplyer());
		log.info("Reply remove ===== [rno]" + rno);
		int removeCount = service.remove(rno);
		log.info("Reply remove count ===== " + removeCount);
		return removeCount == 1 ? new ResponseEntity<>("Success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/* Get, Delete, Modify는 method가 각각 get, delete, put or patch로
	 * 나뉘기 때문에 YARC에서 확인해야 한다. URL 입력 칸 옆에 method 설정하여 실행 test
	 */
	/* 호오오오옥시나 그 사이에 멍청해졌을까봐 얘기해두면
	 * rno와 bno는 DB에서 FK와 PK로 연결이 되어있기 때문에 remove method가 rno만 받아도
	 * 삭제 처리를 할 수 있는것이다. rno를 찾을 때마다 bno를 입력할 필요가 있다면 게시판 제작은
	 * 말도 안 되게 골치아픈 작업이 되었을 것 
	 */
	
	// 댓글 수정
	// Page734 remove와 마찬가지로 replyer 검증을 위한 annotation 추가. vo는 이미 있었음
	@PreAuthorize("principal.username == #vo.replyer")
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value="/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		vo.setRno(rno);
		log.info("Reply modify ===== [rno]" + rno + "[modify vo]" + vo);
		return service.modify(vo) == 1 ? new ResponseEntity<>("Success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/* 마찬가지로 '친절하게' 하나 적자면 rno와 bno는 FK와 PK로 연결되어있기 때문에 
	 * Reply modify ===== [rno]3[modify vo]ReplyVO
	 * (rno=3, bno=null, reply=CHECK, replyer=YARC, 
	 * replyDate=null, updateDate=null)
	 * 요런식으로 bno가 입력되지 않아 null값이 된다고 하더라도 수정이 정상적으로 이루어진다.
	 * (SQL 명령어를 실행하는 Query문[ReplyMapper.xml에 명시]에 아무 문제도 없기 때문)
	 * 같은 맥락으로 reply외의 값이 수정되지 않는 것은 ReplyMapper.xml에서 update를 
	 * #{reply}만 받고 있기 때문.
	 */
	
}
