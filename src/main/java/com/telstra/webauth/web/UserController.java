package com.telstra.webauth.web;

import com.telstra.webauth.model.User;
import com.telstra.webauth.service.SecurityService;
import com.telstra.webauth.service.UserService;
import com.telstra.webauth.validator.ChangePasswordValidator;
import com.telstra.webauth.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ChangePasswordValidator passwordValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginUser(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String loginAdmin(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        model.addAttribute("users",userService.findAllUsers());
        return "admin";
    }
    
    @RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public String login(Model model) {
       model.addAttribute("error", "Your username and password is invalid.");
       return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    
    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePasswordForm", new User());

        return "changepassword";
    }

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("changePasswordForm") User changePasswordForm,
			BindingResult bindingResult, Model model) {
		passwordValidator.validate(changePasswordForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "changepassword";
		}
		userService.changePassword(changePasswordForm);
		model.addAttribute("message", "Password changed successfully. Login using new credentials.");
		return "changepasswordsuccess";
	}

	@RequestMapping(value = "/accountlocked", method = RequestMethod.GET)
	public String accountLocked(Model model) {
		return "accountlocked";
	}

	@RequestMapping(value = "/passwordexpired", method = RequestMethod.POST)
	public String passwordExpired(@ModelAttribute("changePasswordForm") User changePasswordForm,
			BindingResult bindingResult, Model model) {
		passwordValidator.validate(changePasswordForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "changepassword";
		}
		userService.changePassword(changePasswordForm);
		model.addAttribute("message", "Password changed successfully. Login using new credentials.");
		return "changepasswordsuccess";
	}

	@RequestMapping(value = "/passwordexpired", method = RequestMethod.GET)
	public String passwordExpired(Model model) {
		model.addAttribute("changePasswordForm", new User());
		model.addAttribute("message", "Password expired, Please change your password");
		return "changepassword";
	}
	
}

