package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.mapper.UserMapper;
import com.example.service.AdminService;
import com.example.service.CommentService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account ac = null;

        // 根据角色类型分发到不同 Mapper 查询
        if ("ADMIN".equals(account.getRole())) {
            ac = adminMapper.selectByUsername(account.getUsername());
        } else if ("USER".equals(account.getRole())) {
            ac = userMapper.selectByUsername(account.getUsername());
        }
        if (ObjectUtil.isNull(ac)) {
            throw new CustomException("用户不存在");
        }
        if (!ac.getPassword().equals(account.getPassword())) {
            throw new CustomException("密码错误");
        }
        return Result.success(ac);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register() {
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            adminService.updatePassword(account);
        }else if ("USER".equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

}
