package com.lcy.web;


import com.lcy.service.BlogService;
import com.lcy.service.TagService;
import com.lcy.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
//@RequestMapping("/blog")   // 用来处理请求地址映射的注解
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page", blogService.listBlog(pageable));// 拿到分页的博客
        model.addAttribute("types", typeService.listTypeTop(6));// 拿到TOp分类，先写死6个，后面再优化
        model.addAttribute("tags", tagService.listTagTop(10));// 拿到Top标签
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));

        return "index";
    }

    // 搜索
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);// 把搜索的字符串返回，用来展示在搜索结果的搜索框中
        return "search";
    }

    // 展示单条博客
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));// 取三个最新的博客，放在尾部
        return "_fragments :: newblogList";
    }


}
















