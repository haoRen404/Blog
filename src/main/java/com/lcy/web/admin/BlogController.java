package com.lcy.web.admin;

import com.lcy.po.Blog;
import com.lcy.po.User;
import com.lcy.service.BlogService;
import com.lcy.service.TagService;
import com.lcy.service.TypeService;
import com.lcy.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller // 标记该类是控制器
@RequestMapping("/admin")   // 用来处理请求地址映射的注解
public class BlogController {

    private static final String INPUT = "admin/blogs-input";    // 添加博客的路径
    private static final String LIST = "admin/blogs";   // 博客列表路径
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";    // 请求转发到博客列表路径


    @Autowired
    private BlogService blogService;    // 博客service
    @Autowired
    private TypeService typeService;    // 分类service
    @Autowired
    private TagService tagService;      // 标签service

    // 显示博客列表页面
    // springmvc 通过 Pageable对象和@PageableDefault注解获取分页信息
    // size：每一页的展示的数量，默认为20；sort：用来排序的属性；direction：排序方式，Sort.Direction.DESC是倒叙
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());// 查出分类列表
        model.addAttribute("page", blogService.listBlog(pageable, blog));// 分页的方式显示博客
        return LIST;
    }

    // 局部刷新，分页查询出需要局部刷新的数据
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog)); // 返回分页查询出的数据
        return "admin/blogs :: blogList";   // 返回代码片段，使用h:fragment标签引用
    }

    // 对分类和标签的框框初始化，把值赋进去
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    // 跳转到新增
    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);// 初始化，，把值赋进去
        model.addAttribute("blog", new Blog()); // 新建博客对象，存储新增的博客
        return INPUT; // 跳转新增页面
    }

    // 这才是新增，新增按钮
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {// id是跳转到新增页面时新建的博客对象的id
        setTypeAndTag(model);// 初始化，，把值赋进去
        Blog blog = blogService.getBlog(id);// 查询出指定id的博客
        blog.init();// 进行初始化
        model.addAttribute("blog",blog);// 往前台传数据
        return INPUT;
    }



    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }



}
