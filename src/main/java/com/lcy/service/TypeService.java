package com.lcy.service;

import com.lcy.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TypeService {

    Type saveType(Type type);//新增分类

    Type getType(Long id);// 通过id查询分类

    Type getTypeByName(String name);// 通过名称查询

    Page<Type> listType(Pageable pageable);// 分页查询

    List<Type> listType();  // 获取所有标签

    Type updateType(Long id,Type type);// 修改

    void deleteType(Long id);// 删除
}
