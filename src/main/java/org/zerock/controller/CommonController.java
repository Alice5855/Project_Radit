package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model m) {
		log.info("Access Denied ===== " + auth);
		
		m.addAttribute("msg", "Access Denied");
	}
	
	// *MUST* use GetMapping (page631)
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model m) {
		log.info("error ===== " + error);
		log.info("logout ===== " + logout);
		
		if (error != null) {
			m.addAttribute("error", "Invalid credential");
		}
		
		if (logout != null) {
			m.addAttribute("logout", "Logged out");
		}
		
	}
	
	// Get method로 처리되는 logout
	@GetMapping("/customLogout")
	public void logoutGET() {
		log.info("Custom logout ===== ");
		
	}
}