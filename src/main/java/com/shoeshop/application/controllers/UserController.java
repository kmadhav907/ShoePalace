package com.shoeshop.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.shoeshop.application.entity.User;

@Controller
public class UserController {
	@GetMapping("/")
	public String getHomePage() {
		return "index";	
	}
	
	@GetMapping("/login")
	public String getLoginPage(User user) {
		return "login";
	}
	
	@PostMapping("/login")
	public RedirectView postLoginPage(User user) {
		System.out.println(user.getEmail());
		return new RedirectView("/dashboard");
	}
	@GetMapping("/dashboard")
	public String viewDashBoard() {
		return "dashboard";
	}
	
	

}
