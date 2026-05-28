package com.example.service;

import com.example.entity.Film;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface FilmService {

    //添加电影
    void insert(Film film);

    //根据id跟新
    void updateById(Film film);

    //根据id删除
    void deleteById(Integer id);

    //查询所有电影，自带模糊查询
    List<Film> selectAll(Film film);

    //根据id查询
    Film selectById(int id);

    //模糊分页查询
    public PageInfo<Film> selectPage(int pageNum, int pageSize, Film film);

    //封装的查询电影分数方法
    public void setScore(Film film);

    //随机推荐电影
    List<Film> selectRecommend();
}
