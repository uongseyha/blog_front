package edu.mum.cs544.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import edu.mum.cs544.model.Comment;
import edu.mum.cs544.service.ICommentService;
import edu.mum.cs544.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @RequestMapping(value={"/posts/{postId}/comments"})
    public String getAll(@PathVariable int postId ,Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        session.setAttribute("post",postId);
        long userId = user.getId();
      
        if (user.getEmail().equals("admin@mail.com"))
            model.addAttribute("comments",commentService.getAll(userId, postId));
        else
            model.addAttribute("comments",commentService.getByUserIdAndPostId(userId, postId));
        return "/comment/commentList";
    }

    //==== 2. Create new Comment Form ====
    @RequestMapping(value={"/comment/add"},method= RequestMethod.GET)
    public String AddComment(@ModelAttribute("newcomment") Comment comment, Model model, HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";

        model.addAttribute("newComment", comment);
        return "comment/addComment";
    }

    //==== 3. Save add new Comment ====
    @RequestMapping(value={"/comment/save"}, method = RequestMethod.POST)
    public String saveComment(@Valid @ModelAttribute("newComment") Comment comment, Model model, HttpSession session, BindingResult result, RedirectAttributes attr) {
            if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.comment", result);
            attr.addFlashAttribute("comment", comment);
            return "redirect:/comment/addComment";
        }
        long userId = ((User)session.getAttribute("user")).getId();
        int postId = (int)session.getAttribute("post");
        commentService.add(userId, postId, comment);
        String redirect = "redirect:/posts/"+ postId +"/comments";
        return redirect;
    }

    //=== 4. Edit Form ====
    @RequestMapping(value = "/comment/edit/{id}")
    public String editEntry(@PathVariable int id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";

        int postId = (int)session.getAttribute("post");
        long userId = ((User)session.getAttribute("user")).getId();
        Comment comment = commentService.getByUserIdAndPostIdAndCommentId(userId, postId, id);
        model.addAttribute("comment", comment);
        return "comment/editComment";
    }

    //=== 5. Save Edit ====
    @RequestMapping(value = "/comment/edit/save", method = RequestMethod.POST)
    public String SaveEditComment(@ModelAttribute("comment") Comment comment, HttpSession session) {
        int postId = (int)session.getAttribute("post");
        long userId = ((User)session.getAttribute("user")).getId();
        Comment entity= commentService.getByUserIdAndPostIdAndCommentId(userId, postId, comment.getId());
        entity.setDescription(comment.getDescription());
        entity.setCreateDate(comment.getCreateDate());
        entity.setCreatedPostId(comment.getCreatedPostId());
        entity.setCreatedUserId(comment.getCreatedUserId());
        commentService.add(userId, postId, entity);
        String redirect = "redirect:/posts/"+ postId +"/comments";
        return redirect;
    }

    //=== 6. Delete ====
    @RequestMapping(value = "/comment/delete/{id}")
    public String deletecomment(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/login";

        int postId = (int)session.getAttribute("post");
        long userId = ((User)session.getAttribute("user")).getId();
        commentService.delete(userId, postId, id);
        String redirect = "redirect:/posts/"+ postId +"/comments";
        return redirect;
    }
}
