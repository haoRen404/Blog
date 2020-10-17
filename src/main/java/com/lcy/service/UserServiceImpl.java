package com.lcy.service;

import com.lcy.dao.UserRepository;
import com.lcy.po.User;
import com.lcy.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 验证登录
@Service
public class UserServiceImpl implements UserService {

    // jpa
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        // 验证登录，密码转化为MD5
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
