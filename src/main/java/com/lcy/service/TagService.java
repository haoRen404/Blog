package com.lcy.service;

import com.lcy.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {

    // 修改标签
    Tag saveTag(Tag type);

    // 通过id获取标签
    Tag getTag(Long id);

    // 通过名字获取标签
    Tag getTagByName(String name);

    // 分页
    Page<Tag> listTag(Pageable pageable);

    // 获取全部标签
    List<Tag> listTag();

    //
    List<Tag> listTag(String ids);

    // 添加标签
    Tag updateTag(Long id, Tag type);

    // 删除标签
    void deleteTag(Long id);
}
