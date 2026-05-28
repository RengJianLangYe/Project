package com.example.service;

import com.example.entity.Account;
import com.example.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 管理员业务接口，定义管理员相关操作
 */
public interface AdminService {

    /**
     * 新增管理员
     *
     * @param admin 管理员实体对象，包含用户名、密码、姓名等信息
     */
    void add(Admin admin);

    /**
     * 根据ID删除管理员
     *
     * @param id 要删除的管理员ID
     */
    void deleteById(Integer id);

    /**
     * 更新管理员信息
     *
     * @param admin 包含更新数据的管理员实体对象
     */
    void updateById(Admin admin);

    /**
     * 根据ID查询管理员信息
     *
     * @param id 管理员ID
     * @return 查询到的管理员实体对象，若不存在则返回 null
     */
    Admin selectById(Integer id);

    /**
     * 查询所有管理员信息
     *
     * @param admin 查询条件封装对象，可为空或包含筛选条件
     * @return 管理员信息列表
     */
    List<Admin> selectAll(Admin admin);

    /**
     * 分页查询管理员信息
     *
     * @param admin  查询条件封装对象，可为空
     * @param pageNum  当前页码，从1开始
     * @param pageSize 每页记录数
     * @return PageInfo 对象，包含分页结果数据
     */
    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    /**
     * 管理员登录验证
     *
     * @param account 登录账号对象，包含用户名和密码
     * @return 登录成功返回账户信息对象，否则抛出异常
     * @throws com.example.exception.CustomException 用户名或密码错误时抛出
     */
    Account login(Account account);

    /**
     * 修改管理员密码
     *
     * @param account 包含用户名、原密码和新密码的账户对象
     * @throws com.example.exception.CustomException 用户不存在或原密码错误时抛出
     */
    void updatePassword(Account account);
}