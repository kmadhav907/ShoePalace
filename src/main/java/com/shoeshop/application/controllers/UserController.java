package com.shoeshop.application.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.shoeshop.application.entity.User;
import com.shoeshop.application.repos.UserRepository;
import com.shoeshop.application.utils.GlobalUtils;

@Controller
public class UserController {
	@Autowired
	ApplicationContext context;

	@GetMapping("/")
	public String getHomePage() {
		return "index";
	}

	@GetMapping("/login")
	public String getLoginPage(User user) {
		return "login";
	}

	@PostMapping("/login")
	public RedirectView postLoginPage(User user, RedirectAttributes attributes) {
		UserRepository repo = context.getBean(UserRepository.class);
		List<User> users = repo.findByEmail(user.getEmail());
		if(users.isEmpty()) {
			attributes.addFlashAttribute("error", "No user Exists");
			return new RedirectView("/login");
		}
		return new RedirectView("/dashboard");
	}

	@GetMapping("/dashboard")
	public String viewDashBoard() {
		return "dashboard";
	}

	@GetMapping("/register")
	public String getRegisterPage(User user) {
		return "register";
	}

	@PostMapping("/register")
	public RedirectView postRegisterPage(User user, RedirectAttributes attributes) {

		if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
			attributes.addFlashAttribute("error", "Fill out everything");
			return new RedirectView("/register");
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			attributes.addFlashAttribute("error", "Passwords dont match");
			return new RedirectView("/register");
		}
		UserRepository repo = context.getBean(UserRepository.class);
		List<User> users = repo.findByEmail(user.getEmail());
		if (!users.isEmpty()) {
			attributes.addFlashAttribute("error", "User already exists");
			return new RedirectView("/register");
		}
		try {
			String hashedPassword = GlobalUtils.hashPassword(user.getPassword());
			System.out.println(hashedPassword);
			User newUser = new User(user.getName(), user.getEmail(), "user", hashedPassword);
			newUser.setConfirmPassword("");
			newUser.setCreatedTime(LocalDateTime.now().toString());
			repo.save(newUser);
			return new RedirectView("/dashboard");
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attributes.addFlashAttribute("error", "Something went wrong");
		return new RedirectView("/register");

	}

}
