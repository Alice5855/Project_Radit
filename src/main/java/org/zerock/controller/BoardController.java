package org.zerock.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // Spring?? bean?�서 Controller�? ?�식?? ?? ?�도�? ??
@Log4j
@RequestMapping("/board/*") // '/board/'�? url pattern ?�의
@AllArgsConstructor // ?�성?? ?�동 ?�성
public class BoardController {
	// BoardController가 BoardService?? ?�존?�이�? ?�문?? 
	// @AllArgsConstructor �? ?�성?��? 만들�? ?�동?�로 주입?�켜�?. ?�성?��? 
	// ?�성?��? ?�았?? 경우 @Setter(onMethod_ = {@Autowired})�? 처리??
	private BoardService service;
	
	// register ?�력 page?� ?�록 처리
	// ?�록 ?�업?� post method�? ?�용?�나 get method�? ?�력 page�? '?�어?? ??
	// ?�도�?' BoardController?? method�? 추�??�야 ??
	// ?�래?? register method?? ?�력 page�? 보여주는 ??��?? ?�행
	@GetMapping("/register")
	public void register() {
	}
	
	/* before paging
	 * @GetMapping("/list")
	 * public void list(Model m) {
	 * 		log.info("list");
	 * 		m.addAttribute("list", service.getList());
	 * }
	 */
	/*
	@GetMapping("/list")
	public void list(Criteria cri, Model m) {
		log.info("list ===== " + cri);
		
		List<BoardVO> boardVOList = new ArrayList<BoardVO>();
		boardVOList = service.getList(cri);
		for (BoardVO boardVO : boardVOList) {
			boardVO.setU_email(getBoardWriter(boardVO));
		}
		
		m.addAttribute("list", boardVOList);
		int total = service.getTotal(cri);
		log.info("total ===== " + total);
		m.addAttribute("pageMaker", new PageDTO(cri, total));
		// pageMaker?�는 ?�름?? pageDTO 객체�? Model?? ?�성?�로 추�?
		// 교재 page 324
	}
	*/
	@GetMapping("/list")
	public void list(Criteria cri, Model m) {
	    List<BoardVO> boardVOList = new ArrayList<BoardVO>();
	    boardVOList = service.getList(cri);
	    for (BoardVO boardVO : boardVOList) {
	    	boardVO.setU_email(getBoardWriter(boardVO));
	    }
	    m.addAttribute("list", boardVOList);
		int total = service.getTotal(cri);
		log.info("total ===== " + total);
		m.addAttribute("pageMaker", new PageDTO(cri, total));
    }
    
    public String getBoardWriter(BoardVO boardVO) {

	
	
	public String getBoardWriter(BoardVO boardVO) {
		
		return service.getU_nameFromU_Email(boardVO.getU_email());
	}
	
	
	// 첨�? ?�일 list�? ?�어?�기 ?�한 method
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long b_number) {
		log.info("getAttachList ===== " + b_number);
		return new ResponseEntity<List<BoardAttachVO>>(service.getAttachList(b_number), HttpStatus.OK);
	}
	
	// Page712 added need of authentication
	// Only Logged in user can access
	//@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes ratt) {
		log.info("register ===== " + board);
		service.register(board); // 첨�??�일 ?�이??, 게시�? ?�이?? ?? ?�음.
		
		
		// adding file upload feature
		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}

		 
		/*zzzzzzzzzzzz*/
	
//		String str = null;
//		str += board.getAttachList().get(0).getB_uploadPath();
//		str += board.getAttachList().get(0).getB_uuid();
//		str += board.getAttachList().get(0).getB_fileName();
		
		// 쿼리?? ?�요?�거 : bno
		
//		service.setBoardImage(board.getB_number(), str);
		
		
		ratt.addFlashAttribute("result", board.getB_number());
		return "redirect:/board/list";
	}
	
	/* before file upload feature added
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes ratt) {
		log.info("register ===== " + board);
		service.register(board);
		ratt.addFlashAttribute("result", board.getb_number());
		return "redirect:/board/list";
	}
	 */
	/*
	 * RedirectAttributes?? result�? ?�달?�고 "redirect: " ?�두?��? ?�용?�여
	 * response.sendRedirect()�? spring mvc?�서 처리?�게 ??
	 * RedirectAttributes 객체?? addFlashAttribute() method?? ?�회??
	 * data�? HttpSession?? 보�??�여 브라?��??? ?�달??
	 */
	
	
	/* 조회?? ?�록�? ?�사?�게 BoardController�? ?�용?? 처리?? ?? ?�음. ?�별?? 경우�?
	 * ?�하�? 조회?? Get 방식?? ?�용??
	 * C(reate)R(ead)U(pdate)D(elete)�? R만이 Get방식?�로 ?�행
	 * modify ?? 게시글?? 불러?�기 ?�해 get method?? modify mapping?? 추�?
	 * @GetMapping("/get")
	 * public void get(@RequestParam("b_number") Long b_number, Model m) ...
	 */
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("b_number") Long b_number, @ModelAttribute("cri") Criteria cri, Model m) {
		// @ModelAttribute : ?�동?�로 모델?? ?�이?��? 지?�한 ?�름?�로 ?�아�?
		// ?�노?�이?? ?�이?? parameter?? 객체�? ?�해 ?�달?? ?��?�? 명시?? 지?�을 ?�해
		// ?�노?�이?�을 ?�용
		// log.info("get ===== " + b_number);
		log.info("get or modify ===== " + b_number);
		m.addAttribute("board", service.get(b_number));
	}
	
	
	// Get content from Modal WIP
	
	// *TESTING* Json mapping for get from modal
	@GetMapping(value="/getModal", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<BoardVO> getModal(Long b_number) {
		log.info("getModal ===== " + b_number);
		return new ResponseEntity<BoardVO>(service.get(b_number), HttpStatus.OK);
	}

	// Get content from Modal WIP
	
	
	// BoardController?? get() method?�는 b_number 값을 명시?�으�? 처리?�는
	// @RequestParam?? ?�용??(?�라미터명과 변?�명?? 기�??�로 ?�작?�기 ?�문?? ?�략 가??)
	// view�? 게시물을 ?�달?�기 ?�하?? Model?? Parameter�? 지??
	
	// Page712 added need of authentication
	// Only if author of entry is username can access to modify
	// #board.writer : BoardVO(board)?? writer�? 명시?�여 검증절�?
	// @PreAuthorize("principal.username == #board.writer")
	// update?? 경우 BoardVO parameter�? ?�용?? ?�정?�고 BoardService�? ?�출
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes ratt) {
		log.info("modify ===== " + board);
		if (service.modify(board)) {
			ratt.addFlashAttribute("result", "success");
		}
		// service.modify() method?? ?�정 ?��?�? boolean type?�로 처리?��?�?
		// ?�정?? ?�공?? 경우 true�? 반환?�여 if문을 ?�행?�다
		ratt.addAttribute("pageNum", cri.getPageNum());
		ratt.addAttribute("amount", cri.getAmount());
		// page 346 redirect?? attribute ?��?
		ratt.addAttribute("type", cri.getType());
		ratt.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// Page712 added need of authentication
	// Only if author of entry is username can access to remove
	// Parameter�? writer�? 받아 검증절�?
	// @PreAuthorize("principal.username == #writer")
	// remove()�? ??�� 처리 ?? ?? RedirectAttributes�? list?�이지�? ?�동?�킴
	@PostMapping("/remove")
	// public String remove(@RequestParam("u_email") String u_email, @RequestParam("b_number") Long b_number, @ModelAttribute("cri") Criteria cri, RedirectAttributes ratt) {
	// Gotta use this code *AFTER* security added
	public String remove(@RequestParam("b_number") Long b_number, @ModelAttribute("cri") Criteria cri, RedirectAttributes ratt) {
		// RequestParam?� view?? form data가 submit?? ?�에 ?�송?? data?? key값을
		// 기�??�로 value값을 받아?�는 �?. @RequestParam("writer")?? writer??
		// form-data?? writer?� 맞아?? ?�고, @PreAuthorize?�서 검증을 ?�한 #writer
		// ?? form-data?? remove?? parameter�? 받아?? writer?? *변?�명*�? 맞아?�한?? 
		log.info("remove ===== " + b_number);
		
		List<BoardAttachVO> attachList = service.getAttachList(b_number);
		// Added(page581)
		
		if (service.remove(b_number)) {
			deleteFiles(attachList);
			// Added(page581)
			
			
			ratt.addFlashAttribute("result", "success");
		}
		// service.remove() method?? ?�정 ?��?�? boolean type?�로 처리?��?�?
		// ??��?? ?�공?? 경우 true�? 반환?�여 if문을 ?�행?�다
		/*
		ratt.addAttribute("pageNum", cri.getPageNum());
		ratt.addAttribute("amount", cri.getAmount());
		// page 346 redirect?? attribute ?��?
		ratt.addAttribute("type", cri.getType());
		ratt.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
		*/
		return "redirect:/board/list" + cri.getListLink();
		// Criteria?? ?�로 ?�성?? method ?�용(page 581)
	}
	
	// Page581 actual file delete
	// DB?? file data�? 먼�? ??��?�고 ?�제 ?�일?? ??��?�야 ??. ?�일?? ??��?�기 ?�해?�는 ?�당
	// 게시물의 첨�??�일 목록?? ?�요?�데, 첨�??�일 ?�보�? 미리 준비해?�고 DB data�? ??��?�고
	// ?? ?? ?�제 ?�일?? ??��
	private void deleteFiles(List<BoardAttachVO> attachList) {
   
		if(attachList == null || attachList.size() == 0) {
			return;
		}
   
		log.info("delete attach files...................");
		log.info(attachList);
   
		attachList.forEach(attach -> {
			try {        
				Path file  = Paths.get("C:\\Uploaded\\" + attach.getB_uploadPath() + "\\" + attach.getB_uuid() + "_" + attach.getB_fileName());
   
				Files.deleteIfExists(file);
   
				if(Files.probeContentType(file).startsWith("image")) {
   
					Path thumbNail = Paths.get("C:\\Uploaded\\" + attach.getB_uploadPath() + "\\sthumb_" + attach.getB_uuid() + "_" + attach.getB_fileName());
             
					Files.delete(thumbNail);
				}
   
			} catch (Exception e) {
				log.error("delete file error" + e.getMessage());
			} // catch
		}); // forEach
	}
	// java.nio.file package?? Path�? ?�용?�여 처리
}
