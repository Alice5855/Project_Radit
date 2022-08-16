package org.zerock.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {
	// Page613
	@GetMapping("/all")
	public void doAll() {
		log.info("Anyone can access to /all");
	}
	
	@GetMapping("/member")
	public void doMember() {
		log.info("Only members can access to /member");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("Only admin can access to /admin");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doMember2() {
		log.info("Logined annotation member");
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		log.info("Admin annotation Only");
	}
}
