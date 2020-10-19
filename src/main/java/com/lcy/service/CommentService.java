package com.lcy.service;

import com.lcy.po.Comment;

import java.util.List;

// 评论
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);// 通过id获取评论

    Comment saveComment(Comment comment);// 保存评论


}
