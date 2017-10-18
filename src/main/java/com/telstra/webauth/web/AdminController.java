package com.telstra.webauth.web;

import com.telstra.webauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

	@RequestMapping(value = "/accountunlock/{username}", method = RequestMethod.GET)
	public String accountUnlock(Model model, @PathVariable("username") String userName) {
		userService.unlock(userName);
		model.addAttribute("message", "User Account unlocked successfully.");
		return "admin";
	}
	
	@RequestMapping(value = "/resetpassword/{username}", method = RequestMethod.GET)
	public String resetPassword(Model model, @PathVariable("username") String userName) {
		String randomPassword = userService.resetPassword(userName);
		model.addAttribute("message", "Password reset to " + randomPassword);
		return "admin";
	}
}

