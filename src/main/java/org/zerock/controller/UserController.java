package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

   @PostMapping("/regist")
   public String regist(UserVO user) {
      log.info("회원가입 ===== " + user);
      userService.regist(user);
      return "redirect:/board/list";
   }
   
   @PostMapping("/update")
   public String update(UserVO user) {
      log.info("회원정보 수정");
      userService.update(user);
      
      
      log.info(user);
      
      
      return "redirect:/board/list";
      
   }
   
   
   
   @PostMapping("/deleteAccount")
   public String deleteAccount(UserVO user) {
      
      log.info("remove ===== " + user);
      
      userService.deleteAccount(user.getU_Email());
      return "redirect:/board/list";
   }
   
   


   
   
   
   
}