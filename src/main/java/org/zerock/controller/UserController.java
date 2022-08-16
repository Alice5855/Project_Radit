package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.UserVO;
import org.zerock.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller 
@Log4j
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
//	@GetMapping("/register")
//	public void register() {
//	}
	
	@PostMapping("/register")
	public String register(UserVO user, RedirectAttributes ratt) {
		log.info("register ===== " + user);
		// adding file upload feature
		
		userService.register(user);
		ratt.addFlashAttribute("result", user.getU_Email());
		return "redirect:/home.jsp";
	}
	
	
}
