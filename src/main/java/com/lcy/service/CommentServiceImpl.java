package com.lcy.service;

import com.lcy.dao.CommentRepository;
import com.lcy.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // 获取评论，做层级处理
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
//        Sort sort = new Sort(Sort.Direction.DESC, "createTime");// 倒叙
        Sort sort = new Sort("createTime");// 不要倒叙，让新评论出现在下面

        // 数据返回之前先做层级处理
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);

        // return commentRepository.findByBlogId(blogId, sort);
        return eachComment(comments);
    }


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


    /**
     * 循环每个顶级的评论节点.评论层级处理的三个方法之一
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合. 评论层级处理的三个方法之一
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱. 评论层级处理的三个方法之一
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}























