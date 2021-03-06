package com.lcy.service;

import com.lcy.po.Blog;
import com.lcy.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    // 查询博客
    Blog getBlog(Long id);

    // 获取博客，进行博客详情展示
    Blog getAndConvert(Long id);

    // 分页
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    // 分页
    Page<Blog> listBlog(Pageable pageable);

    // 分页查询某标签的博客
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    // 搜素
    Page<Blog> listBlog(String query,Pageable pageable);

    // 获取最新的博客，最新推荐
    List<Blog> listRecommendBlogTop(Integer size);

    // 归档，同一年的存放到一个Map中
    Map<String, List<Blog>> archiveBlog();

    // 归档，查出博客总条数
    Long countBlog();

    // 新增博客
    Blog saveBlog(Blog blog);

    // 修改博客
    Blog updateBlog(Long id,Blog blog);

    // 删除博客
    void deleteBlog(Long id);
}
