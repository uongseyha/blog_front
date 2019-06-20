package edu.mum.cs544.controller;

import edu.mum.cs544.model.User;
import edu.mum.cs544.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(@ModelAttribute("loginForm") User user, Model model) {
		model.addAttribute("loginForm", user);
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String addLogin(@ModelAttribute("loginForm") @Valid User user, BindingResult result, Model model, HttpSession session) {

		if (result.hasErrors()) return "/login";

		User u=userService.isCorrectUser(user.getEmail(), user.getPassword());
		if (u!=null){

			session.setAttribute("user",u);
			return "redirect:/";

//			if (u.getEmail().equals("admin@mail.com")){
//				model.addAttribute("admin", u);
//				return "home/admin";
//			}
//
//			model.addAttribute("user", u);
//			return "home/user";
		}

		model.addAttribute("invalid", true);
		return "login";
	}
}
