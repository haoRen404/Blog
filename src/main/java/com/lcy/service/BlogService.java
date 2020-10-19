package com.lcy.service;

import com.lcy.po.Blog;
import com.lcy.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BlogService {

    // 查询博客
    Blog getBlog(Long id);

    // 分页
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    // 分页
    Page<Blog> listBlog(Pageable pageable);

    // 搜素
    Page<Blog> listBlog(String query,Pageable pageable);

    // 获取最新的博客，最新推荐
    List<Blog> listRecommendBlogTop(Integer size);

    // 修改博客
    Blog saveBlog(Blog blog);

    // 新增博客
    Blog updateBlog(Long id,Blog blog);

    // 删除博客
    void deleteBlog(Long id);
}
