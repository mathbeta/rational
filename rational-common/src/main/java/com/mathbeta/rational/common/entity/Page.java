package com.mathbeta.rational.common.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/13.
 */
public class Page<Entity extends BaseEntity> {
    private int total;
    private int pageNum;
    private int pageSize;
    private int start;
    private int end;
    private Map<String, Object> params;
    private List<Entity> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Entity> getRows() {
        return rows;
    }

    public void setRows(List<Entity> rows) {
        this.rows = rows;
    }
}
