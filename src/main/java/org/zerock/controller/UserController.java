package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.HttpResource;
import org.zerock.domain.UserVO;
import org.zerock.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller 
@Log4j
@AllArgsConstructor
@RequestMapping("/user/*") 
public class UserController {
	
	private UserService userService;
	
//	@GetMapping("/regist")
//	public void register() {
//	}
	
//	@PostMapping("/regist")
//	public String regist(UserVO user, RedirectAttributes ratt) {
//		log.info("회원가입 ===== " + user);
//		// adding file upload feature
//		
//		userService.regist(user);
//		ratt.addFlashAttribute("result", user.getU_Email());
//		return "redirect:/board/list";
//	}
	
	
	@PostMapping("/regist")
	public String regist(UserVO user) {
		log.info("회원가입 ===== " + user);
		// adding file upload feature
		userService.regist(user);
		
		return "/board/list";
	}
	
	@PostMapping("/update")
	public String update(UserVO user) {
		log.info("회원정보 수정");
		userService.update(user);
		
		return "/board/list";
		
	}
	
	@PostMapping("/deleteAccount")
	public String deleteAccount(UserVO user) {
		
		log.info("remove ===== " + user);
		
		userService.deleteAccount(user.getU_Email());
		return "/board/list";
		// Criteria에 새로 생성한 method 사용(page 581)
	}
	

	

	
}
