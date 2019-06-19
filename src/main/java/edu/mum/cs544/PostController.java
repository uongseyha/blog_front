package edu.mum.cs544;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostController {
    private static final String ADMIN = "admin@mail.com";

    @Autowired
    private IPostService postService;

    @GetMapping("/posts")
    public String getAll(Model model, HttpSession session) {
        model.addAttribute("posts", postService.getAll());

        User user = (User) session.getAttribute("user");
        if (user != null && user.getEmail().equals("admin@mail.com"))
            model.addAttribute("canEditAndDelete", true);

        return "post/postList";
    }

    @GetMapping("/posts/me")
    public String getAllByUserId(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "redirect:/login";

        model.addAttribute("canEditAndDelete", true);
        model.addAttribute("posts", postService.getAllByUserId(user.getId()));
        return "post/postList";
    }


    @GetMapping("/posts/add")
    public String addPostForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";

        if (!model.containsAttribute("post"))
            model.addAttribute("post", new Post());
        return "post/postForm";
    }

    @PostMapping("/posts/save")
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attr, HttpSession session) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.post", result);
            attr.addFlashAttribute("post", post);
            return "redirect:/posts/add";
        }

        User user = (User) session.getAttribute("user");
        Post postNew = null;
        if (post.getUserId() != null && !post.getUserId().equals(user.getId()))
            postNew = postService.add(post.getUserId(), post);
        else
            postNew = postService.add(user.getId(), post);

        session.setAttribute("post", postNew);
        return "redirect:/posts/me";
    }

    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable int id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";

        Post post = postService.get(id);
        model.addAttribute("post", post);
        return "post/postForm";
    }

    @GetMapping("/posts/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        postService.delete(id);
        session.removeAttribute("post");
        User user = (User) session.getAttribute("user");
        if (user.getEmail().equals(ADMIN))
            return "redirect:/posts";
        else
            return "redirect:/posts/me";
    }


}
