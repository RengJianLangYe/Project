package com.example.entity;

/**
 * 公告实体类
 * 实体类和数据库字段对应
 */
public class Notice {
    private int id; //主键id
    private String title; //公告标题
    private String content; //公告内容
    private String time; //时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
