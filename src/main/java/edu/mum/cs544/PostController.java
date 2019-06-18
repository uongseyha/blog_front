package edu.mum.cs544;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {
    private static final String ADMIN = "admin@mail.com";

    @Autowired
    private IPostService postService;

    @GetMapping("/posts")
    public String getAll(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";
        else if (user.getEmail().equals(ADMIN))
            model.addAttribute("posts", postService.getAll());
        else
            model.addAttribute("posts", postService.getAllByUserId(user.getId()));

        return "post/postList";
    }


    @GetMapping("/posts/add")
    public String addPostForm(@ModelAttribute("post") Post post) {
        return "post/addPostForm";
    }

    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute("post") Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post postNew = postService.add(user.getId(), post);
        session.setAttribute("post", postNew);
        Post post2 = (Post) session.getAttribute("post");
        System.out.println( post2 );
        return "redirect:/posts";
    }


}
