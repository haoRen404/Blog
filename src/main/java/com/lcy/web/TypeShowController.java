package com.lcy.web;


import com.lcy.po.Type;
import com.lcy.service.BlogService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {

        List<Type> types = typeService.listTypeTop(1024);// 取出1024个分类，足够大的数，能够取出所有分类
        if (id == -1) { // 初始化id是-1，如果id是-1则说明是刚进入分类页，没有点击某个分类
            id = types.get(0).getId();// 把第一个标签的id赋值过去，默认是展示第一个分类的博客
        }
        BlogQuery blogQuery = new BlogQuery();// 查询对象
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);// 返回标签列表
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));// 分页查询
        model.addAttribute("activeTypeId", id);

        return "types";
    }

}
