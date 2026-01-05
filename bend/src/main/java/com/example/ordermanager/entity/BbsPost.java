package com.example.ordermanager.entity;

import java.util.Date;

public class BbsPost {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long boardId;
    private Integer viewCount;
    private Date createTime;
    private Date updateTime;

    // 空构造
    public BbsPost() {}

    // 全参构造
    public BbsPost(Long id, String title, String content, Long userId, Long boardId, Integer viewCount, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.boardId = boardId;
        this.viewCount = viewCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getBoardId() { return boardId; }
    public void setBoardId(Long boardId) { this.boardId = boardId; }
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}