package com.example.ordermanager.entity;

import java.util.List;

public class DataWrapper {
    private List<BbsPost> data; // 分页数据
    private int totalPage; // 总页数

    // getter/setter
    public List<BbsPost> getData() { return data; }
    public void setData(List<BbsPost> data) { this.data = data; }
    public int getTotalPage() { return totalPage; }
    public void setTotalPage(int totalPage) { this.totalPage = totalPage; }
}