package com.lcy.dao;

import com.lcy.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    // 继承JpaSpecificationExecutor<Blog>可以在分页中调用一个方法blogRepository.findAll()

    // 查询出最想的推荐博客，用来展示在博客首页
    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);

    // 搜索。“?1”表示第一个参数，?2表示第二个参数
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.description like ?1") // 通过标题、内容或描述来搜索
    Page<Blog> findByQuery(String query,Pageable pageable);

    // 浏览次数+1
    @Transactional
    @Modifying  // 通知spring date这是一个修改或删除操作
    @Query("update  Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

}












