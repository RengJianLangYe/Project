package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentMapper {
    //插入
    public void insert(Comment comment);

    //根据id更新
    public void updateById(Comment comment);

    //根据id删除
    void deleteById(Integer id);  // 同步改为Integer

    //带模糊查询的全选
    List<Comment> selectAll(Comment comment);

    //统计电影评论
    @Select("select count(*) from comment where film_id = #{filmId}")
    int selectTotal(Integer id);

    //电影评分求和
    @Select("select sum(score) from comment where film_id = #{filmId}")
    Double selectSum(Integer id);
}
