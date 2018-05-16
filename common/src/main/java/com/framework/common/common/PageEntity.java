package com.framework.common.common;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageEntity<T> implements Serializable {
	
    private static final long serialVersionUID = 1894215806791350563L;

    private int pageNo = 1;
    private int pageSize = 20; // 默认为每页20条记录
    private long totalCount = 0;
    private int totalPage = 0;
    private List<T> result = Collections.emptyList();

    public PageEntity(List<T> list) {
        PageInfo<T> page = new PageInfo<T>(list);
        this.PageInit(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page.getList());
    }

    public PageEntity(int pageNo, int pageSize, long totalCount, int totalPage, List<T> result) {
        this.PageInit(pageNo, pageSize, totalCount, totalPage, result);
    }

    private void PageInit(int pageNo, int pageSize, long totalCount, int totalPage, List<T> result) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.result = result;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
