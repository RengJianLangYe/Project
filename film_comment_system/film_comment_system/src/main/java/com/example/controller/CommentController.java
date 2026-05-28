package com.example.controller;


import com.example.common.Result;
import com.example.entity.Comment;
import com.example.entity.Film;
import com.example.service.CommentService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "评论管理", description = "增删改查")
@RestController //标识是controller
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    /**
     * 插入评论
     * @param comment
     * @return
     */
    @PostMapping("/insert")
    @Operation(summary = "添加评论", description = "插入评论(没问题")
    public Result insert(@RequestBody Comment comment) {
        commentService.insert(comment);
        return Result.success();
    }

    /**
     * 根据id更新
     * @param comment
     * @return
     */
    @PostMapping("/updateById")
    @Operation(summary = "根据id更新评论", description = "根据ID更新评论（没问题")
    public Result updateById(@RequestBody Comment comment) {
        commentService.updateById(comment);
        return Result.success("更新成功");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "删除评论")
    public Result deleteById(@PathVariable Integer id) {  // 改为Integer包装类型
        if (id == null) {
            return Result.error("ID不能为空");
        }
        commentService.deleteById(id);
        return Result.success("删除成功");
    }

    /**
     * 查询所有
     * 全选方式：comment="",type=""
     * @param comment
     * @return
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有评论", description = "查询所有评论（没问题）")
    public Result selectAll(Comment comment){
        List<Comment> commentList = commentService.selectAll(comment);
        return Result.success(commentList);
    }

    /**
     * 分页查询电影（支持条件查询）
     */
    @GetMapping("/selectPage")
    @Operation(summary = "分页查询评论", description = "支持按名称分页查询（没问题")
    public Result selectPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            Comment comment) {
        return Result.success(commentService.selectPage(pageNum, pageSize,comment ));
    }




}
