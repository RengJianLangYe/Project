package com.example.service.impl;

import com.example.entity.Category;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.example.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 插入
     * @param comment
     */
    @Override
    public void insert(Comment comment) {
        commentMapper.insert(comment);
    }

    /**
     * 根据id更新
     * @param comment
     */
    @Override
    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {  // 同步改为Integer
        if (id == null) {
            throw new IllegalArgumentException("ID不能为空");
        }
        commentMapper.deleteById(id);
    }

    /**
     * 带模糊查询的全选
     * @param comment
     * @return
     */
    @Override
    public List<Comment> selectAll(Comment comment) {
        List<Comment> commentList = commentMapper.selectAll(comment);
        return commentList;
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param comment
     * @return
     */
    @Override
    public PageInfo<Comment> selectPage(int pageNum, int pageSize, Comment comment) {
        PageHelper.startPage(pageNum,pageSize); //开启分页查询
        List<Comment> commentList=commentMapper.selectAll(comment);   //查询所有数据
        PageHelper.clearPage(); // 关闭分页插件
        return PageInfo.of(commentList);
    }
}
