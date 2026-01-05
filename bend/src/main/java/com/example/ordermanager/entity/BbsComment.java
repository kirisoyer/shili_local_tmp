package com.example.ordermanager.entity;

import java.util.Date;

public class BbsComment {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private Long parentId;
    private Date createTime;

    // 空构造
    public BbsComment() {}

    // 全参构造
    public BbsComment(Long id, String content, Long postId, Long userId, Long parentId, Date createTime) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.parentId = parentId;
        this.createTime = createTime;
    }

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}