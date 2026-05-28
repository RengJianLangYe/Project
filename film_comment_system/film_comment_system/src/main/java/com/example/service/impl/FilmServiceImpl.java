package com.example.service.impl;

import com.example.entity.Film;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.example.service.FilmService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {

    @Resource
    private FilmMapper filmMapper;
    @Resource
    private CommentMapper commentMapper;

    /**
     * 插入电影
     */
    @Override
    public void insert(Film film) {
        filmMapper.insert(film);
    }

    /**
     * 更新电影信息
     */
    @Override
    public void updateById(Film film) {
        filmMapper.updateById(film);
    }

    /**
     * 根据ID删除电影
     */
    @Override
    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }


    /**
     * 查询所有电影（支持模糊查询）
     */
    @Override
    public List<Film> selectAll(Film film) {
        return filmMapper.selectAll(film);
    }

    /**
     * 根据ID查询电影详情
     */
    @Override
    public Film selectById(int id) {
        Film film = filmMapper.selectById(id);
        this.setScore(film);
        return film;
    }

    /**
     * 分页查询电影（支持按名称条件查询）
     */
    @Override
    public PageInfo<Film> selectPage(int pageNum, int pageSize, Film film) {
        PageHelper.startPage(pageNum, pageSize);
        List<Film> films = filmMapper.selectAll(film);

        for (Film f : films) {
           this.setScore(f);
        }

        return new PageInfo<>(films);
    }

    /**
     * 查询电影评分的方法
     * @param film
     */
    @Override
    public void setScore(Film film) {
        // 查询当前这个电影有多少条评论
        int total = commentMapper.selectTotal(film.getId());
        film.setCommentNum(total);

        if (total == 0) {
            film.setScore(0D);
        } else {
            // 求电影的平均分
            Double sum = commentMapper.selectSum(film.getId());
            if (sum == null) {
                sum = 0D;
            }
            BigDecimal score = BigDecimal.valueOf(sum)
                    .divide(BigDecimal.valueOf(total), 1, RoundingMode.HALF_UP);
            film.setScore(score.doubleValue());
        }
    }

    /**
     * 随机推荐电影
     * @return
     */
    @Override
    public List<Film> selectRecommend() {
        List<Film> films = this.selectAll(null);
        Collections.shuffle(films); // 打乱电影的排序

        List<Film> filmList = films.stream()
                .limit(3)
                .collect(Collectors.toList());

        for (Film film : filmList) {
            this.setScore(film);
        }

        return filmList;
    }



}
