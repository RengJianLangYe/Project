package com.example.mapper;

import com.example.entity.Category;

import java.util.List;

public interface CategoryMapper {
    //添加分类
    public void insert(Category category);

    //更新分类
    public void updateById(Category category);

    //根据id删除
    public int deleteById(int id);

    //查询所有
    public List<Category> selectAll(Category category);

    //根据id查询
    public Category selectById(int id);

}
