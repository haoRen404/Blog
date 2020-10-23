package com.lcy.service;


import com.lcy.NotFoundException;
import com.lcy.dao.BlogRepository;
import com.lcy.po.Blog;
import com.lcy.po.Type;
import com.lcy.util.MarkdownUtils;
import com.lcy.util.MyBeanUtils;
import com.lcy.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;


@Service
public class BlogServiceImpl implements BlogService {

    // jpa 用来分页
    @Autowired  // 注入
    private BlogRepository blogRepository;

    // 查询博客
    @Override
    public Blog getBlog(Long id) {
        // 直接调用jpa
        return blogRepository.findOne(id);
    }

    // 获取博客，进行博客详情展示
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }

        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));// Markdown 转 HTML
//        blog.setContent(MdToHtmlUtils.convert(content));// Markdown 转 HTML

        blogRepository.updateViews(id);// 每访问一次，浏览次数+1

        return b;
    }

    // 分页
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        // 使用Specification动态查询
        return blogRepository.findAll(new Specification<Blog>() {
            /*
                Spring Data中的知识，是一个用于简化数据库访问，并支持云服务的开源框架
                Root：查询哪个表，这里查询bend Blog对应的表
                CriteriaQuery：查询哪些字段，排序是什么
                CriteriaBuilder：字段之间是什么关系，如何生成一个查询条件，每一个查询条件都是什么方式
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                // 用于暂时存放满足查询条件的集合
                List<Predicate> predicates = new ArrayList<>();

                // 搜索标题时分页
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {   // 标题不为空串或null时
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));    // like，模糊查询
                }

                // 搜索分类时分页
                if (blog.getTypeId() != null) { // 分类id不为null时
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));   // equal，id相等时查询出来
                }

                // 搜索推荐时分页
                if (blog.isRecommend()) {   // 勾选了推荐时
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend())); // equal，是推荐状态时查询出来
                }
                //
                cq.where(predicates.toArray(new Predicate[predicates.size()])); // toArray方法导出为Object类型数组
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    // 搜索
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    // 分页查询某标签的博客
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");

                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    // 获取最新推荐博客
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);

        return blogRepository.findTop(pageable);
    }


    // 归档，同一年的存放到一个list中，list存放在map中，年份是key，list是value
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();// 获取到倒序排序的年份list集合，大到小

//        Map<String, List<Blog>> map = new HashMap<>();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for(String year : years){
//            System.out.println("年份年份==" + year);
            map.put(year, blogRepository.findByYear(year));// 根据年份查询出所有博客，并加入map中
        }

        return map;
    }

    // 归档，查出博客总条数
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


    // 新增博客
    @Transactional  // 开启事务
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) { // id不存在则创建博客
            blog.setCreateTime(new Date());// 设置创建时间为当前时间
            blog.setUpdateTime(new Date());// 设置更新时间为当前时间
            blog.setViews(0);// 浏览次数为0
        } else { // id存在则修改博客
            blog.setUpdateTime(new Date()); // 修改更改时间
        }
        return blogRepository.save(blog);   // 将临时对象实例化到数据库中，当id存在则修改，不存在则创建
    }

    // 修改博客
    @Transactional  // 开启事务
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);// 通过id查询博客
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        // BeanUtils.copyProperties(a, b)将a中的值赋给b
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));// 复制对象非空属性，把博客中非空属性复制给要保存到数据库的b
        b.setUpdateTime(new Date());// 修改更新时间
        return blogRepository.save(b);
    }

    // 删除博客
    @Transactional  // 开启事务
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
