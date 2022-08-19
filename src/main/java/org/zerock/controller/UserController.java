package org.zerock.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@ModelAttribute UserVO user, HttpServletRequest request){
		HttpSession session = request.getSession();

		user = userService.retrieveSessionInfo(user.getU_Email());
		//2022 08 19 오후 8시 23분
		String u_Email = user.getU_Email();
		String u_Name = user.getU_Name();

		session.setAttribute("u_Email", u_Email);
		session.setAttribute("u_Name", u_Name);
//
//		if("P".equals(grade)){
//			session.setAttribute("threeYn", userBean.getThreeYn());
//		}

		Map<String, String> boardParam = new HashMap<String, String>();

		boardParam.put("u_Email", u_Email);

		userService.updateUserConnectedTime(boardParam);

		session.setMaxInactiveInterval(-1); //세션 무한대
		return "redirect:/board/list";
	}
	
	
	
	
	
	
	
	
}
