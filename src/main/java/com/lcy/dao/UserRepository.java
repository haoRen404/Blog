package com.lcy.dao;

import com.lcy.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 使用springboot的jpa
// jpa里有springboot自动生成的数据库增删改查方法，可直接用
public interface UserRepository extends JpaRepository<User,Long> {
    // 通过用户名和密码查询用户
    User findByUsernameAndPassword(String username, String password);

}
