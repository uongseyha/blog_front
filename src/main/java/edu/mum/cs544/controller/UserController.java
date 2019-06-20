package edu.mum.cs544.controller;

import edu.mum.cs544.service.IUserService;
import edu.mum.cs544.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    //==== 1. User List Form ====
    @RequestMapping(value={"/user"})
    public String getAll(Model model, HttpSession session){

        User u= (User)session.getAttribute("user");
        if (u == null)
            return "redirect:/login";
        else if (u.getEmail().equals("admin@mail.com")){
            model.addAttribute("users",userService.getAll());
            model.addAttribute("canEditAndDelete", true);
        }
        else
            model.addAttribute("users",userService.get(u.getId()));


        return "user/userList";
    }

    //==== 2. Create new User Form ====
    @RequestMapping(value={"/user/add"},method= RequestMethod.GET)
    public String AddUser(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("newUser", user);
        return "user/addUser";
    }

    //==== 3. Save add new User ====
    @RequestMapping(value={"/user/save"}, method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("newUser") @Valid User user,BindingResult result, Model model) {

        if (result.hasErrors()) return "user/addUser";

        userService.add(user);
        return "redirect:/user";
    }

    //=== 4. Edit Form ====
    @RequestMapping(value = "/user/edit/{id}")
    public String editEntry(@PathVariable Long id, Model model) {

        User user = userService.get(id);
        model.addAttribute("user", user);
        return "user/editUser";
    }

    //=== 5. Save Edit ====
    @RequestMapping(value = "/user/edit/save", method = RequestMethod.POST)
    public String SaveEditUser(@ModelAttribute("user") @Valid User user, BindingResult result) {

        if (result.hasErrors()) return "redirect:/user/edit/" + user.getId();

        User entity= userService.get(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());

        userService.add(entity);
        return "redirect:/user";
    }

    //=== 6. Delete ====
    @RequestMapping(value = "/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user";
    }
}
