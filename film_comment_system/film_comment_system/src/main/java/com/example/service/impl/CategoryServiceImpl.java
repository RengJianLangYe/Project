package com.example.service.impl;

import com.example.entity.Category;
import com.example.entity.Notice;
import com.example.mapper.CategoryMapper;
import com.example.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有或者模糊查询
     * @return 返回说有数据组成的list
     */
    @Override
    public List<Category> selectAll(Category category) {
        List<Category> list = categoryMapper.selectAll(category);
        return list;
    }

    /**
     * 新增电影分类
     * @param category
     */
    @Override
    public void inster(Category category) {
        categoryMapper.insert(category);
    }

    /**
     * 更新电影分类
     * @param category
     */
    @Override
    public void updateById(Category category) {
        categoryMapper.updateById(category);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Category selectById(Integer id) {
        Category category=categoryMapper.selectById(id);
        return category;
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public int deleteById(Integer id) {
        return categoryMapper.deleteById(id);
    }

    /**
     * 模糊分页查询
     * @param pageNum
     * @param pageSize
     * @param category
     * @return
     */
    @Override
    public PageInfo<Category> selectPage(int pageNum, int pageSize, Category category) {
        PageHelper.startPage(pageNum,pageSize); //开启分页查询
        List<Category> categoryList=categoryMapper.selectAll(category);   //查询所有数据
        return PageInfo.of(categoryList);
    }


}
