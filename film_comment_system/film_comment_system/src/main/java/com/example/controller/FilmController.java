package com.example.controller;


import com.example.common.Result;
import com.example.entity.Film;
import com.example.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "电影内容管理", description = "增删改查")
@RestController //标识是controller
@CrossOrigin    //跨域
@RequestMapping("/film")
public class FilmController {

    @Resource
    private FilmService filmService;

    /**
     * 添加电影
     */
    @PostMapping("/insert")
    @Operation(summary = "添加电影", description = "传入完整的电影信息（没问题")
    public Result insert(@RequestBody Film film) {
        filmService.insert(film);
        return Result.success("添加成功");
    }


    /**
     * 更新电影信息
     */
    @PutMapping("/updateById")
    @Operation(summary = "更新电影", description = "根据ID更新电影信息")
    public Result updateById(@RequestBody Film film) {
        filmService.updateById(film);
        return Result.success("更新成功");
    }

    /**
     * 根据ID删除电影
     */
    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "删除电影", description = "根据电影ID删除记录（没问题")
    public Result deleteById(@PathVariable Integer id) {
        filmService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 查询所有电影（支持模糊查询）
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有电影", description = "支持按名称糊查询（没问题了）")
    public Result selectAll(Film film) {
        List<Film> films = filmService.selectAll(film);

        return Result.success(films);
    }

    /**
     * 根据ID查询电影详情
     */
    @GetMapping("/selectById/{id}")
    @Operation(summary = "根据ID查询电影", description = "返回单个电影对象（没问题了）")
    public Result selectById(@PathVariable int id) {
        Film film = filmService.selectById(id);
        return Result.success(film);
    }

    /**
     * 分页查询电影（支持条件查询）
     */
    @GetMapping("/selectPage")
    @Operation(summary = "分页查询电影", description = "支持按名称分页查询（没问题了")
    public Result selectPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            Film film) {
        return Result.success(filmService.selectPage(pageNum, pageSize, film));
    }

    /***
     * 随机推荐电影每次三个
     * @return
     */
    @GetMapping("/selectRecommend")
    public Result selectRecommend() {
        List<Film> list = filmService.selectRecommend();
        return Result.success(list);
    }

}
