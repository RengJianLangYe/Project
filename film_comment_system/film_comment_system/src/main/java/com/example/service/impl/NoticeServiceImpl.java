package com.example.service.impl;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.example.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 查询notice表的所有数据
     * @return 返回包含所有查询结果的list
     */
    @Override
    public List<Notice> selectAll(Notice notice) {
        List<Notice> list=noticeMapper.selectAll(notice);
        return list;
    }

    /**
     *分页模糊查询
     * @param pageNum 页码
     * @param pageSize 每页个数
     * @return 分页对象，包含数据和分页参数
     */
    @Override
    public PageInfo<Notice> selectPage(int pageNum,int pageSize,Notice notice) {
        PageHelper.startPage(pageNum,pageSize); //开启分页查询
        List<Notice> noticelist=noticeMapper.selectAll(notice);   //查询所有数据
        return PageInfo.of(noticelist);
    }

    /**
     * 添加公告
     * @param notice
     */
    @Override
    public void addNotice(Notice notice) {
        noticeMapper.inster(notice);
    }

    /**
     * 更新公告
     * @param notice
     */
    @Override
    public void updateNotice(Notice notice) {
        noticeMapper.update(notice);
    }

    /**
     * 按照id查询
     * @param id
     * @return
     */
    @Override
    public Notice selectNoticeById(int id) {
        return noticeMapper.selectById(id);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(int id) {
        noticeMapper.deleteById(id);
    }


}
