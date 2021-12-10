package com.geekq.admin.query;

import java.util.ArrayList;
import java.util.List;

public class PageResult {

    private Integer totalCount;
    private Integer pageSize = 10;
    private Integer currentPage = 1;
    private List result;

    public PageResult() {

    }

    public PageResult(Integer totalCount, Integer pageSize,
                      Integer currentPage, List result) {
        super();
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.result = result;
    }

    public static PageResult empty(int pageSize) {
        return new PageResult(0, pageSize, 1, new ArrayList());
    }

    public Integer getTotalPage() {
        return Math.max((totalCount + pageSize - 1) / pageSize, 1);
    }

    public Integer getPrev() {
        return Math.max(currentPage - 1, 1);
    }

    public Integer getNext() {
        return Math.min(currentPage + 1, getTotalPage());
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List getResult() {
        return result;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
