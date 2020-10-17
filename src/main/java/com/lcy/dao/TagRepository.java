package com.lcy.dao;

import com.lcy.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

// 使用springboot的jpa
// jpa里有springboot自动生成的数据库增删改查方法，可直接用
public interface TagRepository extends JpaRepository<Tag,Long> {

    // 通过name查询标签
    Tag findByName(String name);

}
