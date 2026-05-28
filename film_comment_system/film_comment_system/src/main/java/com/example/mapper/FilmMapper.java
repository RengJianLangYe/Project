package com.example.mapper;

import com.example.entity.Film;
import java.util.List;

/**
 * FilmMapper 是电影信息的数据库操作接口。
 * 对应 MyBatis XML 映射文件中的 SQL 语句。
 */
public interface FilmMapper {

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

}