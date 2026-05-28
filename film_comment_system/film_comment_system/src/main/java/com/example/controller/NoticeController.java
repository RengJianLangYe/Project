package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "公告管理", description = "公告增删改查")
@RestController //标识是controller
@CrossOrigin
@RequestMapping("/notice")
public class NoticeController {

    @Resource   //引用
    NoticeService noticeService;
    /**
     * 查询所有
     * @return
     */
    @GetMapping("/selectAll")
    @Operation(summary = "获取所有用户", description = "返回用户列表")
    public Result selectAll(Notice notice) {
        //调用方法
        List<Notice> noticeList = noticeService.selectAll(notice);
        return Result.success(noticeList);
    }

    /**
     * 分页模糊查询
     * @return
     */
    @GetMapping("/selectPage")
    @Operation(summary = "分页模糊查询", description = "返回查询成功或失败")
    public Result selectPage(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "10") int pageSize,
                             Notice notice) {
        System.out.println("接收到的notice对象：" + notice); // 查看是否真的有值

        //调用方法
        PageInfo<Notice> noticePageInfo= noticeService.selectPage(pageNum,pageSize,notice);
        //返回分页对象
        return Result.success(noticePageInfo);
    }

    @PostMapping("/addNotice")
    @Operation(summary = "添加公告", description = "返回查询成功或失败")
    public Result addNotice(@RequestBody Notice notice) {
        noticeService.addNotice(notice);
        return Result.success();
    }

    // 更新公告
    @PostMapping("/update")
    @Operation(summary = "更新公告", description = "返回查询成功或失败")
    public Result update(@RequestBody Notice notice) {
        try {
            noticeService.updateNotice(notice);
            return Result.success();
        } catch (Exception e) {
            return Result.error("更新失败：" + e.getMessage());
        }
    }

    //根据id查询
    @GetMapping("selectNoticeById/{id}")
    @Operation(summary = "根据id查询公告", description = "返回查询成功或失败")
    public Result selectNoticeById(@PathVariable int id) {
        Notice notice = noticeService.selectNoticeById(id);
        if (notice != null) {
            return Result.success(notice);
        } else {
            return Result.error("未找到该公告");
        }
    }

    @DeleteMapping("deleteById/{id}")
    @Operation(summary = "根据ID删除公告", description = "返回成功或失败")
    public Result deleteById(@PathVariable int id) {
        Notice notice = noticeService.selectNoticeById(id);
        if (notice != null) {
            noticeService.deleteById(id);
            return Result.success("删除成功");
        } else {
            return Result.error("未找到该公告");
        }
    }
}
