package com.lcy.service;

import com.lcy.po.User;


public interface UserService {
    // 通过用户名和密码查询用户，即验证登录
    User checkUser(String username, String password);
}
