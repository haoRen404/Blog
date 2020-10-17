package com.lcy.service;

import com.lcy.po.Blog;
import com.lcy.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BlogService {

    // 查询博客
    Blog getBlog(Long id);

    // 分页
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    // 修改博客
    Blog saveBlog(Blog blog);

    // 新增博客
    Blog updateBlog(Long id,Blog blog);

    // 删除博客
    void deleteBlog(Long id);
}
