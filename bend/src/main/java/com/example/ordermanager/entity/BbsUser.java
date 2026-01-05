package com.example.ordermanager.entity;

import java.util.Date;

public class BbsUser {
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private Date createTime;

    // 空构造
    public BbsUser() {}

    // 全参构造
    public BbsUser(Long id, String username, String password, String avatar, Date createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.createTime = createTime;
    }

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}