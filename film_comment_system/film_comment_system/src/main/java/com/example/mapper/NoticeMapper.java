package com.example.mapper;
import com.example.entity.Notice;


import java.util.List;

public interface NoticeMapper {


    List<Notice> selectAll(Notice notice);

    void inster(Notice notice);

    void update(Notice notice);

    Notice selectById(Integer id);


    void deleteById(Integer id);
}
