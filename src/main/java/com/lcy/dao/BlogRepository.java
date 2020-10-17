package com.lcy.dao;

import com.lcy.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    // 继承JpaSpecificationExecutor<Blog>可以在分页中调用一个方法blogRepository.findAll()

}
