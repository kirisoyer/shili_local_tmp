package com.example.ordermanager.entity;

import java.util.Date;

public class BbsBoard {
    private Long id;
    private String boardName;
    private String boardDesc;
    private Date createTime;

    // 空构造
    public BbsBoard() {}

    // 全参构造
    public BbsBoard(Long id, String boardName, String boardDesc, Date createTime) {
        this.id = id;
        this.boardName = boardName;
        this.boardDesc = boardDesc;
        this.createTime = createTime;
    }

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBoardName() { return boardName; }
    public void setBoardName(String boardName) { this.boardName = boardName; }
    public String getBoardDesc() { return boardDesc; }
    public void setBoardDesc(String boardDesc) { this.boardDesc = boardDesc; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}