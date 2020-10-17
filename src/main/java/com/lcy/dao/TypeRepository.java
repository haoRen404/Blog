package com.lcy.dao;

import com.lcy.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

// 使用springboot的jpa
// jpa里有springboot自动生成的数据库增删改查方法，可直接用
public interface TypeRepository extends JpaRepository<Type,Long> {

    // 通过name查询分类
    Type findByName(String name);

}
