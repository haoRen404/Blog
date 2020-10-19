package com.lcy.service;

import com.lcy.NotFoundException;
import com.lcy.dao.TagRepository;
import com.lcy.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    // jpa
    @Autowired
    private TagRepository tagRepository;

    // 修改标签
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // 通过id查询标签
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    // 通过名字查询标签
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    // 分页，查询所有并分页
    @Transactional  // 事务
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    // 查询所有
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    //
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAll(convertToList(ids));
    }

    // 获取Top标签
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    //
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();    // 创建链表
        if (!"".equals(ids) && ids != null) { //
            // split()方法通过指定分隔符对字符串进行切片
            String[] idarray = ids.split(","); // 切片后保存在数组中
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));// 将数组中的元素保存到链表中
            }
        }
        return list;
    }

    // 添加标签
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findOne(id); // 查询该id对应的标签是否存在
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t); // tag属性拷贝给t
        return tagRepository.save(t); // 数据库中存在则修改，不存在则创建
    }


    // 删除
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
