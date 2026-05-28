package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "分类管理", description = "分类增删改查")
@RestController //标识是controller
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/selectAll")
    @Operation(summary = "获取所有分类", description = "返回分类列表")
    public Result selectAll(Category category) {
        List<Category> categoryList = categoryService.selectAll(category);
        return Result.success(categoryList);
    }

    /**
     * 分页模糊查询
     * @return
     */
    @GetMapping("/selectPage")
    @Operation(summary = "分页模糊查询", description = "返回查询成功或失败")
    public Result selectPage(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "10") int pageSize,
                             Category Category) {
        System.out.println("接收到的Category对象：" + Category); // 查看是否真的有值

        //调用方法
        PageInfo<Category> CategoryPageInfo=categoryService.selectPage(pageNum,pageSize,Category);
        //返回分页对象
        return Result.success(CategoryPageInfo);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加分类", description = "返回添加结果")
    public Result insert(@RequestBody Category category) {
        categoryService.inster(category);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    @Operation(summary = "根据id查询", description = "返回查询结果")
    public Result selectById(@PathVariable int id) {
        Category category = categoryService.selectById(id);
        if (category != null) {
            return Result.success(category);
        } else {
            return Result.error("未找到该分类");
        }
    }

    @PostMapping("/updateById")
    @Operation(summary = "更新", description = "返回查询结果")
    public Result updateById(@RequestBody Category category) {
        try {
            categoryService .updateById(category);
            return Result.success();
        } catch (Exception e) {
            return Result.error("更新失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "根据id删除", description = "返回删除结果")
    public Result deleteById(@PathVariable Integer id) {
        Category category1 = categoryService.selectById(id);
        if (category1 != null) {
            categoryService.deleteById(id);
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }



}
