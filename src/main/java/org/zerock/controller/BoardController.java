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

@Controller // Spring?? bean?ì„œ Controllerë¡? ?¸ì‹?? ?? ?ˆë„ë¡? ??
@Log4j
@RequestMapping("/board/*") // '/board/'ë¡? url pattern ?•ì˜
@AllArgsConstructor // ?ì„±?? ?ë™ ?ì„±
public class BoardController {
	// BoardControllerê°€ BoardService?? ?˜ì¡´?ì´ê¸? ?Œë¬¸?? 
	// @AllArgsConstructor ë¡? ?ì„±?ë? ë§Œë“¤ê³? ?ë™?¼ë¡œ ì£¼ì…?œì¼œì¤?. ?ì„±?ë? 
	// ?ì„±?˜ì? ?Šì•˜?? ê²½ìš° @Setter(onMethod_ = {@Autowired})ë¡? ì²˜ë¦¬??
	private BoardService service;
	
	// register ?…ë ¥ page?€ ?±ë¡ ì²˜ë¦¬
	// ?±ë¡ ?‘ì—…?€ post methodë¥? ?¬ìš©?˜ë‚˜ get methodë¡? ?…ë ¥ pageë¥? '?½ì–´?? ??
	// ?ˆë„ë¡?' BoardController?? methodë¥? ì¶”ê??´ì•¼ ??
	// ?„ë˜?? register method?? ?…ë ¥ pageë¥? ë³´ì—¬ì£¼ëŠ” ??• ?? ?˜í–‰
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
		// pageMaker?¼ëŠ” ?´ë¦„?? pageDTO ê°ì²´ë¥? Model?? ?ì„±?¼ë¡œ ì¶”ê?
		// êµì¬ page 324
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
	
	
	// ì²¨ë? ?Œì¼ listë¥? ?½ì–´?¤ê¸° ?„í•œ method
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
		service.register(board); // ì²¨ë??Œì¼ ?°ì´??, ê²Œì‹œë¬? ?°ì´?? ?? ?ˆìŒ.
		
		
		// adding file upload feature
		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}

		 
		/*zzzzzzzzzzzz*/
	
//		String str = null;
//		str += board.getAttachList().get(0).getB_uploadPath();
//		str += board.getAttachList().get(0).getB_uuid();
//		str += board.getAttachList().get(0).getB_fileName();
		
		// ì¿¼ë¦¬?? ?„ìš”?œê±° : bno
		
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
	 * RedirectAttributes?? resultë¥? ?„ë‹¬?˜ê³  "redirect: " ?‘ë‘?´ë? ?¬ìš©?˜ì—¬
	 * response.sendRedirect()ë¥? spring mvc?ì„œ ì²˜ë¦¬?˜ê²Œ ??
	 * RedirectAttributes ê°ì²´?? addFlashAttribute() method?? ?¼íšŒ??
	 * dataë¥? HttpSession?? ë³´ê??˜ì—¬ ë¸Œë¼?°ì??? ?„ë‹¬??
	 */
	
	
	/* ì¡°íšŒ?? ?±ë¡ê³? ? ì‚¬?˜ê²Œ BoardControllerë¥? ?´ìš©?? ì²˜ë¦¬?? ?? ?ˆìŒ. ?¹ë³„?? ê²½ìš°ë¥?
	 * ?œí•˜ë©? ì¡°íšŒ?? Get ë°©ì‹?? ?¬ìš©??
	 * C(reate)R(ead)U(pdate)D(elete)ì¤? Rë§Œì´ Getë°©ì‹?¼ë¡œ ?˜í–‰
	 * modify ?? ê²Œì‹œê¸€?? ë¶ˆëŸ¬?¤ê¸° ?„í•´ get method?? modify mapping?? ì¶”ê?
	 * @GetMapping("/get")
	 * public void get(@RequestParam("b_number") Long b_number, Model m) ...
	 */
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("b_number") Long b_number, @ModelAttribute("cri") Criteria cri, Model m) {
		// @ModelAttribute : ?ë™?¼ë¡œ ëª¨ë¸?? ?°ì´?°ë? ì§€?•í•œ ?´ë¦„?¼ë¡œ ?´ì•„ì¤?
		// ?´ë…¸?Œì´?? ?†ì´?? parameter?? ê°ì²´ë¥? ?µí•´ ?„ë‹¬?? ?˜ì?ë§? ëª…ì‹œ?? ì§€?•ì„ ?„í•´
		// ?´ë…¸?Œì´?˜ì„ ?¬ìš©
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
	
	
	// BoardController?? get() method?ëŠ” b_number ê°’ì„ ëª…ì‹œ?ìœ¼ë¡? ì²˜ë¦¬?˜ëŠ”
	// @RequestParam?? ?´ìš©??(?Œë¼ë¯¸í„°ëª…ê³¼ ë³€?˜ëª…?? ê¸°ì??¼ë¡œ ?™ì‘?˜ê¸° ?Œë¬¸?? ?ëµ ê°€??)
	// viewë¡? ê²Œì‹œë¬¼ì„ ?„ë‹¬?˜ê¸° ?„í•˜?? Model?? Parameterë¡? ì§€??
	
	// Page712 added need of authentication
	// Only if author of entry is username can access to modify
	// #board.writer : BoardVO(board)?? writerë¥? ëª…ì‹œ?˜ì—¬ ê²€ì¦ì ˆì°?
	// @PreAuthorize("principal.username == #board.writer")
	// update?? ê²½ìš° BoardVO parameterë¡? ?´ìš©?? ?¤ì •?˜ê³  BoardServiceë¥? ?¸ì¶œ
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes ratt) {
		log.info("modify ===== " + board);
		if (service.modify(board)) {
			ratt.addFlashAttribute("result", "success");
		}
		// service.modify() method?? ?˜ì • ?¬ë?ë¥? boolean type?¼ë¡œ ì²˜ë¦¬?˜ë?ë¡?
		// ?˜ì •?? ?±ê³µ?? ê²½ìš° trueë¥? ë°˜í™˜?˜ì—¬ ifë¬¸ì„ ?¤í–‰?œë‹¤
		ratt.addAttribute("pageNum", cri.getPageNum());
		ratt.addAttribute("amount", cri.getAmount());
		// page 346 redirect?? attribute ? ì?
		ratt.addAttribute("type", cri.getType());
		ratt.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// Page712 added need of authentication
	// Only if author of entry is username can access to remove
	// Parameterë¡? writerë¥? ë°›ì•„ ê²€ì¦ì ˆì°?
	// @PreAuthorize("principal.username == #writer")
	// remove()ë¡? ?? œ ì²˜ë¦¬ ?? ?? RedirectAttributesë¡? list?˜ì´ì§€ë¡? ?´ë™?œí‚´
	@PostMapping("/remove")
	// public String remove(@RequestParam("u_email") String u_email, @RequestParam("b_number") Long b_number, @ModelAttribute("cri") Criteria cri, RedirectAttributes ratt) {
	// Gotta use this code *AFTER* security added
	public String remove(@RequestParam("b_number") Long b_number, @ModelAttribute("cri") Criteria cri, RedirectAttributes ratt) {
		// RequestParam?€ view?? form dataê°€ submit?? ?Œì— ?„ì†¡?? data?? keyê°’ì„
		// ê¸°ì??¼ë¡œ valueê°’ì„ ë°›ì•„?¤ëŠ” ê²?. @RequestParam("writer")?? writer??
		// form-data?? writer?€ ë§ì•„?? ?˜ê³ , @PreAuthorize?ì„œ ê²€ì¦ì„ ?„í•œ #writer
		// ?? form-data?? remove?? parameterë¡? ë°›ì•„?? writer?? *ë³€?˜ëª…*ê³? ë§ì•„?¼í•œ?? 
		log.info("remove ===== " + b_number);
		
		List<BoardAttachVO> attachList = service.getAttachList(b_number);
		// Added(page581)
		
		if (service.remove(b_number)) {
			deleteFiles(attachList);
			// Added(page581)
			
			
			ratt.addFlashAttribute("result", "success");
		}
		// service.remove() method?? ?˜ì • ?¬ë?ë¥? boolean type?¼ë¡œ ì²˜ë¦¬?˜ë?ë¡?
		// ?? œ?? ?±ê³µ?? ê²½ìš° trueë¥? ë°˜í™˜?˜ì—¬ ifë¬¸ì„ ?¤í–‰?œë‹¤
		/*
		ratt.addAttribute("pageNum", cri.getPageNum());
		ratt.addAttribute("amount", cri.getAmount());
		// page 346 redirect?? attribute ? ì?
		ratt.addAttribute("type", cri.getType());
		ratt.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
		*/
		return "redirect:/board/list" + cri.getListLink();
		// Criteria?? ?ˆë¡œ ?ì„±?? method ?¬ìš©(page 581)
	}
	
	// Page581 actual file delete
	// DB?? file dataë¥? ë¨¼ì? ?? œ?˜ê³  ?¤ì œ ?Œì¼?? ?? œ?´ì•¼ ??. ?Œì¼?? ?? œ?˜ê¸° ?„í•´?œëŠ” ?´ë‹¹
	// ê²Œì‹œë¬¼ì˜ ì²¨ë??Œì¼ ëª©ë¡?? ?„ìš”?œë°, ì²¨ë??Œì¼ ?•ë³´ë¥? ë¯¸ë¦¬ ì¤€ë¹„í•´?ê³  DB dataë¥? ?? œ?˜ê³ 
	// ?? ?? ?¤ì œ ?Œì¼?? ?? œ
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
	// java.nio.file package?? Pathë¥? ?´ìš©?˜ì—¬ ì²˜ë¦¬
}
