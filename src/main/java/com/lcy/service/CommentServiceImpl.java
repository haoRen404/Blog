package com.lcy.service;

import com.lcy.dao.CommentRepository;
import com.lcy.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return commentRepository.findByBlogId(blogId, sort);
    }

//    @Override
//    public List<Comment> listCommentByBlogId(Long blogId) {
//        Sort sort = new Sort("createTime");
//        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
//        return eachComment(comments);
//    }

    // 添加评论
    @Transactional  // 事务
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();// 获取父评论的id
        if (parentCommentId != -1) {    // 回复评论
            comment.setParentComment(commentRepository.findOne(parentCommentId));// 设置父评论
        } else {    // 进行评论
          comment.setParentComment(null);// 设置父评论为null，即没有父评论
        }
        comment.setCreateTime(new Date());// 设置评论的时间
        return commentRepository.save(comment);
    }
}























