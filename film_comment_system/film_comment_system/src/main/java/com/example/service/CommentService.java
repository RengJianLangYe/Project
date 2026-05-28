package com.example.service;

import com.example.entity.Category;
import com.example.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommentService {
    //插入
    public void insert(Comment comment);

    //根据id更新
    public void updateById(Comment comment);

    //根据id删除
    public void deleteById(Integer id);

    //带模糊查询的全选
    List<Comment> selectAll(Comment comment);

    //分页查询
    PageInfo<Comment> selectPage(int pageNum, int pageSize, Comment comment);
}
