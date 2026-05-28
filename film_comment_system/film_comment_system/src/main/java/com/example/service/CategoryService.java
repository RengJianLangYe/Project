package com.example.service;

import com.example.entity.Category;
import com.example.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    //查询所有或者模糊查询
    List<Category> selectAll(Category category);

    //新增分类
    void inster(Category category);

    //更新分类
    void updateById(Category category);

    //根据id查询分类
    Category selectById(Integer id);

    //根据id删除分类
    int deleteById(Integer id);

    //分页查询
    PageInfo<Category> selectPage(int pageNum, int pageSize, Category category);
}
