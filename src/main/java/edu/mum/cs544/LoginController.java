package edu.mum.cs544;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String addLogin(@ModelAttribute("loginForm") User user, Model model) {
//		List<User> users=userService.getUsers();
//		for(User u: users) {
//			if(u.getUserName().equals(loginForm.getUserName()) && u.getUserPassword().equals(loginForm.getPassword())) {
//				if(u.getRole().getId()==Long.valueOf(1)) {
//					model.addAttribute("admin", userService.getUserByUserId(u.getId()));
//					return "home/admin";
//				}else if(u.getRole().getId()==Long.valueOf(2)) {
//					model.addAttribute("faculty", userService.getUserByUserId(u.getId()));
//					return "home/faculty";
//				}
//					model.addAttribute("student", userService.getUserByUserId(u.getId()));
//					return "home/student";
//			}
//		}
		User u=userService.isCorrectUser(user.getEmail(), user.getPassword());
		if (u!=null){

			if (u.getEmail().equals("admin@mail.com")){
				model.addAttribute("admin", u);
				return "home/admin";
			}

			model.addAttribute("user", u);
			return "home/user";
		}

		model.addAttribute("invalid", true);
		return "login";
	}
}
