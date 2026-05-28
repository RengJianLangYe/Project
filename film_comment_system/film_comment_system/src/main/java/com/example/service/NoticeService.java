package com.example.service;

import com.example.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoticeService {

    //获取全部公告
    public List<Notice> selectAll(Notice notice);

    //模糊分页查询
    public PageInfo<Notice> selectPage(int pageNum,int pageSize,Notice notice);

    //添加公告
    void addNotice(Notice notice);

    //更新公告
    public void updateNotice(Notice notice);

    //按照id查询
    public Notice selectNoticeById(int id);

    //根据id删除公告
    public void deleteById(int id);
}
