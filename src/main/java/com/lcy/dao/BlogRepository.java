package com.lcy.dao;

import com.lcy.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    // 继承JpaSpecificationExecutor<Blog>可以在分页中调用一个方法blogRepository.findAll()

    // 查询出最想的推荐博客，用来展示在博客首页
    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);

}
