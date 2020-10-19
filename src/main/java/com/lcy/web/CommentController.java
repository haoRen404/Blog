package com.lcy.web;

import com.lcy.po.Comment;
import com.lcy.service.BlogService;
import com.lcy.service.CommentService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar")
    private String avatar;  // 头像的地址，在配置文件中配置了值


    // 返回代码片段，局部刷新评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));

        return "blog :: commentList";
    }

    // 提交评论
    @PostMapping("/comments")
    public String post(Comment comment){
        Long blogId = comment.getBlog().getId();// 获取所属博客的id
        comment.setBlog(blogService.getBlog(blogId));// 所属博客
        comment.setAvatar(avatar);// 头像
        commentService.saveComment(comment);// 保存评论

        return "redirect:/comments/" + comment.getBlog().getId();
    }

}















