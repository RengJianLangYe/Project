package com.example.service;

import com.example.entity.Account;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户业务接口，定义用户相关操作
 */
public interface UserService {

    /**
     * 新增用户
     *
     * @param user 用户实体对象，包含用户名、密码、姓名等信息
     */
    void add(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 要删除的用户ID
     */
    void deleteById(Integer id);

    /**
     * 更新用户信息
     *
     * @param user 包含更新数据的用户实体对象
     */
    void updateById(User user);

    /**
     * 根据ID查询用户信息
     *
     * @param id 用户ID
     * @return 查询到的用户实体对象，若不存在则返回 null
     */
    User selectById(Integer id);

    /**
     * 查询所有用户信息
     *
     * @param user 查询条件封装对象，可为空或包含筛选条件
     * @return 用户信息列表
     */
    List<User> selectAll(User user);

    /**
     * 分页查询用户信息
     *
     * @param user  查询条件封装对象，可为空
     * @param pageNum  当前页码，从1开始
     * @param pageSize 每页记录数
     * @return PageInfo 对象，包含分页结果数据
     */
    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    /**
     * 用户登录验证
     *
     * @param account 登录账号对象，包含用户名和密码
     * @return 登录成功返回账户信息对象，否则抛出异常
     * @throws com.example.exception.CustomException 用户名或密码错误时抛出
     */
    Account login(Account account);

    /**
     * 修改用户密码
     *
     * @param account 包含用户名、原密码和新密码的账户对象
     * @throws com.example.exception.CustomException 用户不存在或原密码错误时抛出
     */
    void updatePassword(Account account);
}