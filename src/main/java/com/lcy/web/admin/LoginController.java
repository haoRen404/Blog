package com.lcy.web.admin;

import com.lcy.po.User;
import com.lcy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

// 处理登录

@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    // 登录
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);// checkUser 验证登录
        if (user != null) {
            user.setPassword(null);// 密码设为空，避免暴露
            session.setAttribute("user", user);// 把用户添加到session中，代表该用户处于登录状态
            return "admin/index";// 验证通过，跳转到博客管理首页
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";// 验证失败，重定向到登陆页面
        }
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");// 删除session中的user，退出登录
        return "redirect:/admin";
    }
}
